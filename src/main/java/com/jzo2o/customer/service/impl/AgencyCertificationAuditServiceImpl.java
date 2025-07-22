package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.customer.mapper.AgencyCertificationAuditMapper;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.AgencyCertificationAuditService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class AgencyCertificationAuditServiceImpl extends ServiceImpl<AgencyCertificationAuditMapper,AgencyCertificationAudit> implements AgencyCertificationAuditService {

    @Resource
    private JwtTool jwtTool;
    @Resource
    private AgencyCertificationAuditMapper agencyCertificationAuditMapper;

    /**
     * 添加机构认证审核记录
     * @param agencyCertificationAuditAddReqDTO 添加审核参数对象
     */
    @Override
    public void addAgencyCertificationAudit(AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO, String token) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long id = currentUserInfo.getId();
        AgencyCertificationAudit agencyCertificationAudit =
                BeanUtils.copyProperties(agencyCertificationAuditAddReqDTO, AgencyCertificationAudit.class);
        agencyCertificationAudit.setServeProviderId(id);
        agencyCertificationAuditMapper.insert(agencyCertificationAudit);
    }
    
    /**
     * 根据token获取拒绝原因
     *
     * @param token 用户令牌
     * @return 拒绝原因响应DTO
     */
    @Override
    public RejectReasonResDTO findRejectReason(String token) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long id = currentUserInfo.getId();
        AgencyCertificationAudit one = lambdaQuery().eq(AgencyCertificationAudit::getServeProviderId, id).one();
        String rejectReason = one.getRejectReason();
        RejectReasonResDTO rejectReasonResDTO = new RejectReasonResDTO();
        rejectReasonResDTO.setRejectReason(rejectReason);
        return rejectReasonResDTO;
    }
}
