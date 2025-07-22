package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.customer.mapper.WorkerCertificationAuditMapper;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.WorkerCertificationAuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkerCertificationAuditServiceImpl extends ServiceImpl<WorkerCertificationAuditMapper, WorkerCertificationAudit> implements WorkerCertificationAuditService {
    @Resource
    private JwtTool jwtTool;

    @Resource
    private WorkerCertificationAuditMapper workerCertificationAuditMapper;

    /**
     * 添加服务人员认证审核记录
     * @param workerCertificationAuditAddReqDTO 服务人员认证审核添加请求模型
     * @param token                             用户令牌
     */
    @Override
    public void addWorkerCertificationAudit(WorkerCertificationAuditAddReqDTO workerCertificationAuditAddReqDTO, String token) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long id = currentUserInfo.getId();
        WorkerCertificationAudit workerCertificationAudit = BeanUtils.copyProperties(workerCertificationAuditAddReqDTO, WorkerCertificationAudit.class);
        workerCertificationAudit.setServeProviderId(id);
        int insert = workerCertificationAuditMapper.insert(workerCertificationAudit);
        System.out.println(insert);

    }

    /**
     * 查询服务人员认证审核驳回信息
     * @param token 用户令牌
     * @return 服务人员认证审核拒绝原因
     */
    @Override
    public RejectReasonResDTO findRejectReason(String token) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long id = currentUserInfo.getId();
        WorkerCertificationAudit one = lambdaQuery().eq(WorkerCertificationAudit::getServeProviderId, id).one();
        RejectReasonResDTO rejectReasonResDTO = new RejectReasonResDTO();
        rejectReasonResDTO.setRejectReason(one.getRejectReason());
        return rejectReasonResDTO;
    }
}
