package com.jzo2o.customer.service;
import com.baomidou.mybatisplus.extension.service.IService;

import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;

import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import io.undertow.util.BadRequestException;

public interface AgencyCertificationAuditService extends IService<AgencyCertificationAudit> {

    /**
     * 添加机构认证审核记录
     *
     * @param agencyCertificationAuditAddReqDTO 机构认证审核添加请求模型
     * @param token
     */
    void addAgencyCertificationAudit(AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO, String token);

    /**
     * 根据token查询机构认证审核拒绝原因
     *
     * @param token 用户令牌
     * @return 拒绝原因响应模型
     */
    RejectReasonResDTO findRejectReason(String token);

    /**
     * 服务端分页查询机构认证审核信息
     *
     * @param reqDTO 请求模型
     * @return 分页结果
     */
    PageResult<AgencyCertificationAudit> selectAgencyCertificationAuditPageQuery(AgencyCertificationAuditPageQueryReqDTO reqDTO);

    /**
     * 审核机构认证
     *
     * @param id            机构认证ID
     * @param status        审核状态（1：通过，2：拒绝）
     * @param rejectReason  拒绝原因（当status为2时必填）
     * @param authHeader    授权头部信息
     */
    void auditAgency(Long id, Integer status, String rejectReason, String authHeader) throws BadRequestException;
}
