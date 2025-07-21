package com.jzo2o.customer.service;

import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AddressBook;
import com.jzo2o.customer.model.dto.request.AddressBookPageQueryReqDTO;
import com.jzo2o.customer.model.dto.request.AddressBookUpsertReqDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 地址薄 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-07-06
 */
public interface IAddressBookService extends IService<AddressBook> {

    /**
     * 根据用户id和城市编码获取地址
     *
     * @param userId 用户id
     * @param cityCode 城市编码
     * @return 地址编码
     */
    List<AddressBookResDTO> getByUserIdAndCity(Long userId, String cityCode);


    /**
     * 新增地址簿
     * @param addressBookUpsertReqDTO 地址信息
     */
    void addAddressBook(AddressBookUpsertReqDTO addressBookUpsertReqDTO, String token);

    /**
     * 分页查询地址薄
     * @param addressBookPageQueryReqDTO 分页查询请求
     */
    PageResult<AddressBookResDTO> pageAddressBook(AddressBookPageQueryReqDTO addressBookPageQueryReqDTO, String authHeader);

    /**
     * 编辑地址簿之前的查询
     */
    AddressBook findAddressBook(Long id, String authHeader);


    /**
     * 修改地址簿
     * @param id 地址簿ID
     * @param addressBookUpsertReqDTO 地址簿更新请求
     * @param authHeader 认证头信息
     */
    void updateAddressBook(Long id, @Valid AddressBookUpsertReqDTO addressBookUpsertReqDTO, String authHeader);

    /**
     * 批量删除地址簿
     * @param ids 地址簿ID列表
     * @param authHeader 认证头信息
     */
    void deleteAddressBook(List<Long> ids, String authHeader);

    /**
     * 设置默认地址
     * @param id 地址簿ID
     * @param flag 标志位，1表示设置为默认地址，0表示取消默认地址
     * @param authHeader 认证头信息
     */
    void setdefaultAddress(Long id, int flag, String authHeader);

    /**
     * 获取默认地址
     * @param authHeader 认证头信息
     */
    AddressBook getDefaultAddress(String authHeader);
}
