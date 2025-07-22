package com.jzo2o.customer.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 服务人员认证审核实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("worker_certification_audit")
public class WorkerCertificationAudit {
    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键  mybatis-plus使用雪花算法生成全局唯一id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 服务人员id
     */
    @TableField("serve_provider_id")
    private Long serveProviderId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @TableField("id_card_no")
    private String idCardNo;

    /**
     * 身份证正面
     */
    @TableField("front_img")
    private String frontImg;

    /**
     * 身份证反面
     */
    @TableField("back_img")
    private String backImg;

    /**
     * 证明资料
     */
    @TableField("certification_material")
    private String certificationMaterial;

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