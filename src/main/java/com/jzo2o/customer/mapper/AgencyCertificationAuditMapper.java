package com.jzo2o.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgencyCertificationAuditMapper extends BaseMapper<AgencyCertificationAudit> {
    /**
     * 查找机构认证审核记录
     * @param reqDTO 机构认证审核对象
     */
    List<AgencyCertificationAudit> selectAgencyCertificationAuditList(@Param("params")AgencyCertificationAuditPageQueryReqDTO reqDTO);
}
