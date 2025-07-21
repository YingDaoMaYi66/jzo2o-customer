package com.jzo2o.customer.controller.consumer;

import com.jzo2o.api.customer.dto.response.AddressBookResDTO;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AddressBook;
import com.jzo2o.customer.model.dto.request.AddressBookPageQueryReqDTO;
import com.jzo2o.customer.model.dto.request.AddressBookUpsertReqDTO;
import com.jzo2o.customer.service.impl.AddressBookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController("consumerAddressController")
@RequestMapping("/consumer/address-book")
@Api(tags = "用户端 - 地址相关接口")
public class AddressController {
    @Resource
    AddressBookServiceImpl addressBookService;

    @ApiOperation("新增地址簿接口")
    @PostMapping
    public void addAddressBook(@RequestBody @Valid AddressBookUpsertReqDTO addressBookUpsertReqDTO,@RequestHeader("Authorization") String authHeader ) {
        // 这里可以调用服务层方法来处理地址簿的新增逻辑
         addressBookService.addAddressBook(addressBookUpsertReqDTO, authHeader);
    }

    @ApiOperation("查询地址簿")
    @GetMapping("/page")
    public PageResult<AddressBookResDTO> pageAddressBook(AddressBookPageQueryReqDTO addressBookPageQueryReqDTO, @RequestHeader("Authorization") String authHeader) {
        // 这里可以调用服务层方法来处理地址簿的分页查询逻辑
        return addressBookService.pageAddressBook(addressBookPageQueryReqDTO, authHeader);
    }

    @ApiOperation("地址簿编辑-地址簿详情")
    @GetMapping("/{id}")
    //修改地址簿前调用次接口先查询
    public AddressBook editAddressBook(@PathVariable("id") Long id, @RequestHeader("Authorization") String authHeader) {
        // 这里可以调用服务层方法来处理地址簿的编辑逻辑
        return addressBookService.findAddressBook(id, authHeader);
    }

    @ApiOperation("地址簿编辑-修改地址")
    @PutMapping("/{id}")
    public void updateAddressBook(@PathVariable("id") Long id, @RequestBody @Valid AddressBookUpsertReqDTO addressBookUpsertReqDTO, @RequestHeader("Authorization") String authHeader) {
        // 这里可以调用服务层方法来处理地址簿的修改逻辑
        addressBookService.updateAddressBook(id, addressBookUpsertReqDTO, authHeader);
    }

    @ApiOperation("删除地址簿")
    @DeleteMapping("/bath")
    public void deletebathAddressBook(@RequestBody List<Long> ids, @RequestHeader("Authorization") String authHeader) {
        // 这里可以调用服务层方法来处理地址簿的删除逻辑
        addressBookService.deleteAddressBook(ids, authHeader);
    }

    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public void setfaultAddress(@RequestParam("id") Long id, @RequestParam("flag") int flag,@RequestHeader("Authorization") String authHeader) {
        // 这里可以调用服务层方法来处理地址簿的设置默认地址逻辑
        addressBookService.setdefaultAddress(id, flag, authHeader);
    }

    @ApiOperation("获取默认地址接口")
    @GetMapping("/defaultAddress")
    public AddressBook defaultAddress(@RequestHeader("Authorization") String authHeader) {
        return addressBookService.getDefaultAddress(authHeader);
    }


}
