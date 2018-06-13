package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.bussiness.domain.SystemAccount;

import java.math.BigDecimal;

public interface ISystemAccountFlowService {
    void systemManagementChargeFlow(SystemAccount account, BigDecimal amount);

    void createInterestManegerFlow(SystemAccount systemAccount, BigDecimal interest);
}
