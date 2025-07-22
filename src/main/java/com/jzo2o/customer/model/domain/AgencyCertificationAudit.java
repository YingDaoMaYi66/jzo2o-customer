package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构认证审核实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("agency_certification_audit")
public class AgencyCertificationAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键（雪花算法生成）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 机构id
     */
    @TableField("serve_provider_id")
    private Long serveProviderId;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    @TableField("id_number")
    private String idNumber;

    /**
     * 法人姓名
     */
    @TableField("legal_person_name")
    private String legalPersonName;

    /**
     * 法人身份证号
     */
    @TableField("legal_person_id_card_no")
    private String legalPersonIdCardNo;

    /**
     * 营业执照
     */
    @TableField("business_license")
    private String businessLicense;

    /**
     * 审核状态（0：未审核，1：已审核）
     */
    @TableField("audit_status")
    private Integer auditStatus;

    /**
     * 审核人id
     */
    @TableField("auditor_id")
    private Long auditorId;

    /**
     * 审核人姓名
     */
    @TableField("auditor_name")
    private String auditorName;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 认证状态（1：认证中，2：认证成功，3：认证失败）
     */
    @TableField("certification_status")
    private Integer certificationStatus;

    /**
     * 驳回原因
     */
    @TableField("reject_reason")
    private String rejectReason;

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
