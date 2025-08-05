package com.jzo2o.customer.controller.operation;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.customer.model.domain.WorkerCertificationAudit;
import com.jzo2o.customer.model.dto.request.WorkerCertificationAuditPageQueryReqDTO;
import com.jzo2o.customer.service.WorkerCertificationAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.undertow.util.BadRequestException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("operationWorkerAuditCertificationController")
@RequestMapping("/operation/worker-certification-audit")
@Api(tags = "运营端 - 服务人员认证审核相关接口")
public class OperationWorkerCertificationController {
    @Resource
    private WorkerCertificationAuditService workerCertificationAuditService;

    @GetMapping("/page")
    @ApiOperation("审核服务人员认证分页查询")
    public PageResult<WorkerCertificationAudit> workerCertificationaudit( WorkerCertificationAuditPageQueryReqDTO reqDTO) {
        return  workerCertificationAuditService.selectWorkerCertificationAuditPageQuery(reqDTO);
    }

    @PutMapping("/audit/{id}")
    @ApiOperation("审核服务人员认证")
    public void audit(@PathVariable("id") Long id,
                      @RequestParam("certificationStatus") Integer status, @RequestParam(required = false) String rejectReason,
    @RequestHeader("Authorization") String authHeader) throws BadRequestException {
        workerCertificationAuditService.audit(id, status, rejectReason,authHeader);
    }


}
