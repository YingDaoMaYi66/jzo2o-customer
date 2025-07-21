package com.jzo2o.customer.controller.worker;

import com.jzo2o.customer.model.domain.BankAccount;
import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.service.BankSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("workerBankAccountController")
@RequestMapping("/worker/bank-account")
@Api(tags = "服务人员端 - 银行账户相关接口")
public class WorkerBankAccount {
    @Resource
    private BankSetService bankSetService;

    @ApiOperation("服务人员端新增或更新银行账号信息")
    @PostMapping
    public void workerAddBankAccount(@RequestBody BankAccountUpsertReqDTO bankAccountUpsertReqDTO,@RequestHeader("Authorization") String token) {
        bankSetService.addOrUpdateBankAccount(bankAccountUpsertReqDTO, token);
    }

    @ApiOperation("服务人员端查询银行账号信息")
    @GetMapping("/currentUserBankAccount")
    public BankAccount workerGetBankAccount(@RequestHeader("Authorization") String token) {
        return bankSetService.getBankAccount(token);
    }
}
