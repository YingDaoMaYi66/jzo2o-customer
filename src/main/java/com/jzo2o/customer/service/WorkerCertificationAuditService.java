package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import io.undertow.util.BadRequestException;

public interface WorkerCertificationAuditService extends IService<WorkerCertificationAudit> {

    /**
     * 添加服务人员认证审核
     *
     * @param workerCertificationAuditAddReqDTO 服务人员认证审核添加请求模型
     * @param token                             用户令牌
     */
    void addWorkerCertificationAudit(WorkerCertificationAuditAddReqDTO workerCertificationAuditAddReqDTO, String token);

    /**
     * 查询服务人员认证审核驳回原因
     *
     * @param token 用户令牌
     * @return 服务人员认证审核拒绝原因信息
     */
    RejectReasonResDTO findRejectReason(String token);

    /**
     * 服务端分页查询服务人员认证审核信息
     *
     * @param reqDTO 请求模型
     */
    PageResult<WorkerCertificationAudit> selectWorkerCertificationAuditPageQuery(WorkerCertificationAuditPageQueryReqDTO reqDTO);

    /**
     * 审核服务人员认证
     *
     * @param id           服务人员认证审核ID
     * @param status       审核状态
     * @param rejectReason 驳回原因（可选）
     * @param authHeader
     */
    void audit(Long id, Integer status, String rejectReason, String authHeader) throws BadRequestException;
}
