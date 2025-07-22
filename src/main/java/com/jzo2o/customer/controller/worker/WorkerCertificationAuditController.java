package com.jzo2o.customer.controller.worker;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditAddReqDTO;
import com.jzo2o.customer.model.dto.response.RejectReasonResDTO;
import com.jzo2o.customer.service.WorkerCertificationAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("workerCertificationAuditController")
@RequestMapping("/worker/worker-certification-audit")
@Api(tags = "服务人员端 - 服务人员认证审核相关接口")
public class WorkerCertificationAuditController {
    @Resource
    private WorkerCertificationAuditService workerCertificationAuditService;
    @ApiOperation("服务人员认证审核")
    @PostMapping
    public void workeraudit(@RequestBody WorkerCertificationAuditAddReqDTO
                                        workerCertificationAuditAddReqDTO, @RequestHeader("Authorization") String token) {

         workerCertificationAuditService.addWorkerCertificationAudit(workerCertificationAuditAddReqDTO, token);
    }

    @ApiOperation("查询服务人员认证审核信息")
    @GetMapping("/rejectReason")
    public RejectReasonResDTO rejectReason(@RequestHeader("Authorization") String token) {

        return workerCertificationAuditService.findRejectReason(token);
    }

}
