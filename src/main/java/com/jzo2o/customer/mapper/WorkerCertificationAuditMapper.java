package com.jzo2o.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkerCertificationAuditMapper extends BaseMapper<WorkerCertificationAudit> {

    List<WorkerCertificationAudit> queryWorkerCertificationAuditList(@Param("params") WorkerCertificationAuditPageQueryReqDTO params);
}
