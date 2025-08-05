package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.customer.enums.AuditStatus;
import com.jzo2o.customer.mapper.WorkerCertificationAuditMapper;
import com.jzo2o.customer.mapper.WorkerCertificationMapper;
import com.jzo2o.customer.model.domain.WorkerCertification;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.WorkerCertificationAuditService;
import com.jzo2o.mysql.utils.PageHelperUtils;
import io.undertow.util.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class WorkerCertificationAuditServiceImpl extends ServiceImpl<WorkerCertificationAuditMapper, WorkerCertificationAudit> implements WorkerCertificationAuditService {
    @Resource
    private JwtTool jwtTool;

    @Resource
    private WorkerCertificationAuditMapper workerCertificationAuditMapper;

    @Resource
    private WorkerCertificationMapper workerCertificationMapper;

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

    /**
     * 服务端分页查询服务人员认证审核信息
     * @param reqDTO 请求模型
     */
    @Override
    public PageResult<WorkerCertificationAudit> selectWorkerCertificationAuditPageQuery(WorkerCertificationAuditPageQueryReqDTO reqDTO) {
        PageResult<WorkerCertificationAudit> workerCertificationAuditPageResult = PageHelperUtils.selectPage(reqDTO,
                () -> workerCertificationAuditMapper.queryWorkerCertificationAuditList(reqDTO));
        return workerCertificationAuditPageResult ;
    }

    /**
     * 审核服务人员认证
     *
     * @param id           服务人员认证审核ID
     * @param status       审核状态
     * @param rejectReason 驳回原因（可选）
     * @param authHeader
     */
    @Transactional
    @Override
    public void audit(Long id, Integer status, String rejectReason, String authHeader) throws BadRequestException {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(authHeader);
        Long operatorid = currentUserInfo.getId();
        String operatorname = currentUserInfo.getName();
        LocalDateTime now = LocalDateTime.now();
        // 认证失败
        if(status == 3) {
                  //根据id查询认证记录
            boolean update = lambdaUpdate().eq(WorkerCertificationAudit::getId, id)
                    //设置认证状态为失败
                    .set(WorkerCertificationAudit::getCertificationStatus, status)
                    //设置驳回原因
                    .set(WorkerCertificationAudit::getRejectReason, rejectReason)
                    //设置审核员
                    .set(WorkerCertificationAudit::getAuditorId, operatorid)
                    //设置审核状态
                    .set(WorkerCertificationAudit::getAuditStatus, AuditStatus.auditStatusEd.getStatus())
                    //设置审核员姓名
                    .set(WorkerCertificationAudit::getAuditorName, operatorname)
                    .set(WorkerCertificationAudit::getAuditTime, now)
                    .update();
            if (!update) {
                throw new BadRequestException("审核失败，更新记录失败");
            }
        }
        // 认证通过
            else {
                    //根据id查询更新认证操作
            boolean update = lambdaUpdate().eq(WorkerCertificationAudit::getId, id)
                    //设置认证状态为通过
                    .set(WorkerCertificationAudit::getCertificationStatus, status)
                    //设置审核员ID
                    .set(WorkerCertificationAudit::getAuditorId, operatorid)
                    //设置审核员名称
                    .set(WorkerCertificationAudit::getAuditorName, operatorname)
                    .set(WorkerCertificationAudit::getAuditTime, now)
                    .update();

            WorkerCertificationAudit one = lambdaQuery().eq(WorkerCertificationAudit::getId, id).one();

            WorkerCertification workerCertification = new WorkerCertification();
            //这里的id是服务人员认证审核的ID，不是随机生成的uuid
            workerCertification.setId(one.getServeProviderId());
            workerCertification.setName(one.getName());
            workerCertification.setIdCardNo(one.getIdCardNo());
            workerCertification.setFrontImg(one.getFrontImg());
            workerCertification.setBackImg(one.getBackImg());
            workerCertification.setCertificationStatus(one.getCertificationStatus());
            workerCertification.setCertificationMaterial(one.getCertificationMaterial());
            workerCertification.setCertificationTime(now);
            workerCertificationMapper.addOrUpdate(workerCertification);

        }

    }
}
