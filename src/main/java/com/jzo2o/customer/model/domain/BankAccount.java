package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bank_account")
public class BankAccount {
    /**
     * 服务人员/机构id 主键
     */
    @TableId(type = IdType.INPUT) // 需手动赋值，无自增
    private Long id;

    /**
     * 类型，2：服务人员，3：服务机构
     */
    private Integer type;

    /**
     * 户名
     */
    private String name;

    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 网点
     */
    private String branch;

    /**
     * 银行账号
     */
    private String account;

    /**
     * 开户证明
     */
    @TableField("account_certification")
    private String accountCertification;

    /**
     * 创建时间（自动填充）
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间（自动更新）
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
