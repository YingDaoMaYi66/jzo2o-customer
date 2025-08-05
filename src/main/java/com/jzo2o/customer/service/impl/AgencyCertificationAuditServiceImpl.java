package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.customer.enums.AuditStatus;
import com.jzo2o.customer.mapper.AgencyCertificationAuditMapper;
import com.jzo2o.customer.mapper.AgencyCertificationMapper;
import com.jzo2o.customer.model.domain.AgencyCertification;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.domain.WorkerCertification;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.AgencyCertificationAuditService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import io.undertow.util.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class AgencyCertificationAuditServiceImpl extends ServiceImpl<AgencyCertificationAuditMapper,AgencyCertificationAudit> implements AgencyCertificationAuditService {

    @Resource
    private JwtTool jwtTool;
    @Resource
    private AgencyCertificationAuditMapper agencyCertificationAuditMapper;

    @Resource
    private AgencyCertificationMapper agencyCertificationMapper;

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

    /**
     * 服务端分页查询机构认证审核信息
     *
     * @param reqDTO 请求模型
     * @return 分页结果
     */
    @Override
    public PageResult<AgencyCertificationAudit> selectAgencyCertificationAuditPageQuery(AgencyCertificationAuditPageQueryReqDTO reqDTO) {
        PageResult<AgencyCertificationAudit> agencyCertificationAuditPageResult = PageHelperUtils.selectPage(reqDTO, () -> agencyCertificationAuditMapper.selectAgencyCertificationAuditList(reqDTO));
        return agencyCertificationAuditPageResult;
    }

    @Transactional
    @Override
    public void auditAgency(Long id, Integer status, String rejectReason, String authHeader) throws BadRequestException {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        Long operatorid = currentUserInfo.getId();
        String operatorname = currentUserInfo.getName();
        LocalDateTime now = LocalDateTime.now();
        // 认证失败
        if (status == 3) {
            //根据id查询认证记录
            boolean update = lambdaUpdate().eq(AgencyCertificationAudit::getId, id)
                    //设置认证状态为失败
                    .set(AgencyCertificationAudit::getCertificationStatus, status)
                    //设置驳回原因
                    .set(AgencyCertificationAudit::getRejectReason, rejectReason)
                    //设置审核员
                    .set(AgencyCertificationAudit::getAuditorId, operatorid)
                    //设置审核状态
                    .set(AgencyCertificationAudit::getAuditStatus, AuditStatus.auditStatusEd.getStatus())
                    //设置审核员姓名
                    .set(AgencyCertificationAudit::getAuditorName, operatorname)
                    .set(AgencyCertificationAudit::getAuditTime, now)
                    .update();
            if (!update) {
                throw new BadRequestException("审核失败，更新记录失败");
            }
        } else {
            //根据id查询更新认证操作
            boolean update = lambdaUpdate().eq(AgencyCertificationAudit::getId, id)
                    //设置认证状态为通过
                    .set(AgencyCertificationAudit::getCertificationStatus, status)
                    //设置审核员ID
                    .set(AgencyCertificationAudit::getAuditorId, operatorid)
                    //设置审核员名称
                    .set(AgencyCertificationAudit::getAuditorName, operatorname)
                    .set(AgencyCertificationAudit::getAuditTime, now)
                    .update();
            if (!update) {
                throw new BadRequestException("审核失败，更新记录失败");
            }

            AgencyCertificationAudit one = lambdaQuery().eq(AgencyCertificationAudit::getId, id).one();

            AgencyCertification agencyCertification = new AgencyCertification();
            //这里的id是机构认证审核的ID，不是随机生成的uuid
            agencyCertification.setId(one.getServeProviderId());
            agencyCertification.setName(one.getName());
            agencyCertification.setIdNumber(one.getIdNumber());
            agencyCertification.setLegalPersonName(one.getLegalPersonName());
            agencyCertification.setLegalPersonIdCardNo(one.getLegalPersonIdCardNo());
            agencyCertification.setBusinessLicense(one.getBusinessLicense());
            agencyCertification.setCertificationStatus(one.getCertificationStatus());
            agencyCertification.setCertificationTime(now);
            agencyCertificationMapper.addOrUpdate(agencyCertification);

        }
    }


}
