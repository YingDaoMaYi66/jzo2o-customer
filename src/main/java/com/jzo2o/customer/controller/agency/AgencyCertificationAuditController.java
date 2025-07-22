package com.jzo2o.customer.controller.agency;

import com.jzo2o.customer.model.domain.AgencyCertificationAudit;
import com.jzo2o.customer.model.dto.request.AgencyCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.AgencyCertificationAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customer/agency-certification-audit")
@Api(tags = "服务商端 - 服务商认证审核相关接口")
public class AgencyCertificationAuditController {
    @Resource
    private AgencyCertificationAuditService agencyCertificationAuditService;

    @PostMapping
    @ApiOperation("机构提交认证申请")
    public  void addAgencyCertificationAudit(@RequestBody AgencyCertificationAuditAddReqDTO agencyCertificationAuditAddReqDTO,
                                             @RequestHeader("Authorization") String token) {
        agencyCertificationAuditService.addAgencyCertificationAudit(agencyCertificationAuditAddReqDTO,token);
    }

    @GetMapping("/rejectReason")
    @ApiOperation("查询机构认证审核信息")
    public RejectReasonResDTO rejectReason(@RequestHeader("Authorization") String token) {
        return agencyCertificationAuditService.findRejectReason(token);
    }
}
