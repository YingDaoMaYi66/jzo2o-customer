package com.jzo2o.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.customer.model.domain.BankAccount;

public interface BankAccountMapper extends BaseMapper<BankAccount> {
    // 新增或更新银行账号
    void addOrUpdateBankAccount(BankAccount bankAccount);
}
