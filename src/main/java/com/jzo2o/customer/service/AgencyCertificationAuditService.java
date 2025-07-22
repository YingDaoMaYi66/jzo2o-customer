package com.jzo2o.customer.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;

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
}
