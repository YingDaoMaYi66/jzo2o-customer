package com.jzo2o.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditStatus {
    auditStatusEd(1, "已审核"),
    auditStatusNo(0, "未审核");
    private final int status;
    private final String desc;
}
