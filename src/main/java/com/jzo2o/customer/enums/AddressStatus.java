package com.jzo2o.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddressStatus {
    defaultAddress(1, "默认地址"),
    notDefaultAddress(0, "非默认地址");
    private final int status;
    private final String description;
}
