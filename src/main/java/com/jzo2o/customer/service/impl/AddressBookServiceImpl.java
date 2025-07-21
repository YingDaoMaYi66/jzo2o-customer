package com.jzo2o.customer.service.impl;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.utils.*;
import com.jzo2o.customer.enums.AddressStatus;
import com.jzo2o.customer.mapper.AddressBookMapper;
import com.jzo2o.customer.model.domain.AddressBook;
import com.jzo2o.customer.model.dto.request.AddressBookPageQueryReqDTO;
import com.jzo2o.customer.model.dto.request.AddressBookUpsertReqDTO;
import com.jzo2o.customer.service.IAddressBookService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 地址薄 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-07-06
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {


    @Resource
    private AddressBookMapper addressBookMapper;

    @Resource
    private JwtTool jwtTool;


    /**
     * 地址薄分页查询
     * @param userId 用户id
     * @param city 城市编码
     * @return 地址薄分页结果
     */
    @Override
    public List<AddressBookResDTO> getByUserIdAndCity(Long userId, String city) {

        List<AddressBook> addressBooks = lambdaQuery()
                .eq(AddressBook::getUserId, userId)
                .eq(AddressBook::getCity, city)
                .list();
        if(CollUtils.isEmpty(addressBooks)) {
            return new ArrayList<>();
        }
        return BeanUtils.copyToList(addressBooks, AddressBookResDTO.class);
    }

    /**
     * 新增地址簿
     * @param addressBookUpsertReqDTO 地址信息
     * @param token 用户token
     */
    @Override
    public void addAddressBook(AddressBookUpsertReqDTO addressBookUpsertReqDTO,String token) {
        // 解析token
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        AddressBook addressBook = BeanUtils.copyBean(addressBookUpsertReqDTO, AddressBook.class);

        //如果新增的地址簿是默认地址，则需要将当前用户的其他默认地址设置为非默认
        if (addressBookUpsertReqDTO.getIsDefault()== AddressStatus.defaultAddress.getStatus()) {
            //查询当前用户的默认地址
            List<AddressBook> list = lambdaQuery()
                    .eq(AddressBook::getUserId, currentUserInfo.getId())
                    .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                    .list();
            //如果存在默认地址，则将其设置为非默认
            if (ObjectUtil.isNotEmpty(list)) {
                //如果存在默认地址，则将其设置为非默认
                 lambdaUpdate()
                        .eq(AddressBook::getUserId, currentUserInfo.getId())
                        .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                        .set(AddressBook::getIsDefault, AddressStatus.notDefaultAddress.getStatus())
                        .update();
            }
        }

        //设置地址簿的用户id
        addressBook.setUserId(currentUserInfo.getId());
        //设置地址经纬度
        try{
            String location = addressBookUpsertReqDTO.getLocation();
            String[] split = location.split(",");
            if (split.length == 2) {
                addressBook.setLon(Double.parseDouble(split[0]));
                addressBook.setLat(Double.parseDouble(split[1]));
            }
        }catch (Exception e){
            throw new RuntimeException("地址经纬度格式错误");
        }

        int insert = addressBookMapper.insert(addressBook);
        if (insert <= 0) {
            throw new RuntimeException("新增地址簿失败");
        }
    }

    /**
     *
     * @param addressBookPageQueryReqDTO 分页查询请求
     * @param authHeader
     * @return
     */
    @Override
    public PageResult<AddressBookResDTO> pageAddressBook(AddressBookPageQueryReqDTO addressBookPageQueryReqDTO, String authHeader) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        //用户id
        Long id = currentUserInfo.getId();
        PageResult<AddressBookResDTO> addressBookResDTOPageResult = PageHelperUtils.selectPage(addressBookPageQueryReqDTO,
                () -> addressBookMapper.queryAddressBookList(id));
        return addressBookResDTOPageResult;
    }

    /**
     * 编辑地址簿之前的查询
     * @param id 地址簿id
     * @param authHeader 用户token
     * @return 地址簿信息
     */
    @Override
    public AddressBook findAddressBook(@RequestParam Long id, String authHeader) {
    CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        AddressBook one = lambdaQuery()
                .eq(AddressBook::getUserId, currentUserInfo.getId())
                .eq(AddressBook::getId, id)
                .one();
        if (one != null) {
            AddressBook addressBook= BeanUtils.copyProperties(one, AddressBook.class);
            return addressBook;
        }else {
            return null;
        }
    }

    /**
     * 修改地址簿
     * @param id 地址簿ID
     * @param addressBookUpsertReqDTO 地址簿更新请求
     * @param authHeader 认证头信息
     */
    @Override
    public void updateAddressBook(Long id, AddressBookUpsertReqDTO addressBookUpsertReqDTO, String authHeader) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);

        //如果修改的地址簿是默认地址，则需要将当前用户的其他默认地址设置为非默认
        if (addressBookUpsertReqDTO.getIsDefault()== AddressStatus.defaultAddress.getStatus()) {
            //查询当前用户的默认地址
            List<AddressBook> list = lambdaQuery()
                    .eq(AddressBook::getUserId, currentUserInfo.getId())
                    .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                    .list();
            //如果存在默认地址，则将其设置为非默认
            if (ObjectUtil.isNotEmpty(list)) {
                //如果存在默认地址，则将其设置为非默认
                lambdaUpdate()
                        .eq(AddressBook::getUserId, currentUserInfo.getId())
                        .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                        .set(AddressBook::getIsDefault, AddressStatus.notDefaultAddress.getStatus())
                        .update();
            }
        }
        AddressBook addressBook = BeanUtils.copyBean(addressBookUpsertReqDTO, AddressBook.class);
        lambdaUpdate().eq(AddressBook::getId, id)
                .eq(AddressBook::getUserId, currentUserInfo.getId())
                .update(addressBook);
    }

    /**
     * 批量删除地址簿
     * @param ids 地址簿ID列表
     * @param authHeader 认证头信息
     */
    @Override
    public void deleteAddressBook(List<Long> ids, String authHeader) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        Long Userid = currentUserInfo.getId();
        lambdaUpdate()
                .eq(AddressBook::getUserId, Userid)
                .in(AddressBook::getId, ids)
                .remove();
    }

    @Override
    public void setdefaultAddress(Long id, int flag, String authHeader) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        //如果设置为默认地址，则需要将当前用户的其他默认地址设置为非默认
        if (flag == AddressStatus.defaultAddress.getStatus()) {
                lambdaUpdate()
                        .eq(AddressBook::getUserId, currentUserInfo.getId())
                        .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                        .set(AddressBook::getIsDefault, AddressStatus.notDefaultAddress.getStatus())
                        .update();
                lambdaUpdate()
                        .eq(AddressBook::getId, id)
                        .set(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                        .update();

        }else {
            lambdaUpdate()
                    .eq(AddressBook::getUserId, currentUserInfo.getId())
                    .eq(AddressBook::getId, id)
                    .set(AddressBook::getIsDefault, flag)
                    .update();
        }


    }

    /**
     * 获取默认地址
     * @param authHeader 认证头信息
     * @return 默认地址信息
     */
    @Override
    public AddressBook getDefaultAddress(String authHeader) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        AddressBook one = lambdaQuery()
                .eq(AddressBook::getUserId, currentUserInfo.getId())
                .eq(AddressBook::getIsDefault, AddressStatus.defaultAddress.getStatus())
                .one();
        return one;
    }
}
