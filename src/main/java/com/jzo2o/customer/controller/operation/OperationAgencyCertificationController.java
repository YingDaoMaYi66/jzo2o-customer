package com.jzo2o.customer.controller.operation;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.service.AgencyCertificationAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.undertow.util.BadRequestException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("operationAgencyCertificationController")
@RequestMapping("/operation/agency-certification-audit")
@Api(tags = "运营端 - 服务人员认证审核相关接口")
public class OperationAgencyCertificationController {
    @Resource
    private AgencyCertificationAuditService agencyCertificationAuditService;

    @GetMapping("/page")
    @ApiOperation("审核机构认证分页查询")
    public PageResult<AgencyCertificationAudit> AgencyCertificationaudit(AgencyCertificationAuditPageQueryReqDTO reqDTO) {
        return  agencyCertificationAuditService.selectAgencyCertificationAuditPageQuery(reqDTO);
    }

    @PutMapping("/audit/{id}")
    @ApiOperation("审核机构认证")
    public void audit(@PathVariable("id") Long id,
                      @RequestParam("certificationStatus") Integer status, @RequestParam(required = false) String rejectReason,
    @RequestHeader("Authorization") String authHeader) throws BadRequestException {
        agencyCertificationAuditService.auditAgency(id, status, rejectReason,authHeader);
    }


}
