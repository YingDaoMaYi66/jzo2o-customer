package com.jzo2o.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.customer.mapper.BankAccountMapper;
import com.jzo2o.customer.model.domain.BankAccount;
import com.jzo2o.customer.model.dto.request.BankAccountUpsertReqDTO;
import com.jzo2o.customer.service.BankSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BankSetServiceImpl extends ServiceImpl<BankAccountMapper, BankAccount> implements BankSetService {
    @Resource
    private BankAccountMapper bankAccountMapper;
    @Resource
    private JwtTool jwtTool;

    @Override
    public void addOrUpdateBankAccount(BankAccountUpsertReqDTO bankAccountUpsertReqDTO,String token) {
        // 解析JWT令牌获取当前用户信息
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long userId = currentUserInfo.getId();
        bankAccountUpsertReqDTO.setId(userId);
        BankAccount bankAccount = BeanUtils.copyProperties(bankAccountUpsertReqDTO, BankAccount.class);
        bankAccountMapper.addOrUpdateBankAccount(bankAccount);
    }

    /**
     * 根据token获取当前用户的银行账户信息
     * @param token JWT令牌
     * @return 当前用户的银行账户信息
     */
    @Override
    public BankAccount getBankAccount(String token) {
        CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
        Long id = currentUserInfo.getId();
        return lambdaQuery()
                .eq(BankAccount::getId, id)
                .one();
    }
}
