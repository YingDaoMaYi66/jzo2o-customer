package com.jzo2o.customer.controller.worker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("workerCertificationAuditController")
@RequestMapping("/worker/worker-certification-audit")
@Api(tags = "服务人员端 - 服务人员认证审核相关接口")
public class WorkerCertificationAuditController {
    @ApiOperation("服务人员认证审核")
    @PostMapping
    public void workeraudit() {

    }
}
