package com.jzo2o.customer.mapper;

import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.customer.model.domain.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 地址薄 Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-07-06
 */
public interface AddressBookMapper extends BaseMapper<AddressBook> {

    /**
     * 分页查询地址薄列表
     * @return 地址薄列表的分页结果
     */
    List<AddressBookResDTO> queryAddressBookList(Long userId);
}
