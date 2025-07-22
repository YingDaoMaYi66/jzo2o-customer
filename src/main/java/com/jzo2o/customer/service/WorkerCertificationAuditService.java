package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;

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
}
