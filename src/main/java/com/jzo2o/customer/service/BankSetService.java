package com.jzo2o.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.customer.model.domain.BankAccount;
import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;

public interface BankSetService extends IService<BankAccount> {
    void addOrUpdateBankAccount(BankAccountUpsertReqDTO bankAccountUpsertReqDTO,String token);

    BankAccount getBankAccount(String token);
}
