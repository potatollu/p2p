package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.domain.Account;

import java.math.BigDecimal;

public interface IAccountFlowService {
    void createBaseFlow(Account account, int actionType, BigDecimal amount, String note);
    void createRechargeAccountFlow(Account account, int actionType, BigDecimal amount);
    void createBidFlow(Account account, BigDecimal amount);

    void createAuditErrorFlow(Account account,  BigDecimal amount);

    void creatBorrowSuccess(Account account,  BigDecimal amount);

    void creatManagementChargeFlow(Account borrowAccount, BigDecimal managementCharge);

    void createUnFreedzedFlowAmount(Account bidAccount, BigDecimal availableAmount);

    void createReturnAccountFlow(Account returnAccont, BigDecimal totalAmount);

    void createGetmoneyAccountFllow(Account bidAccount, BigDecimal totalAmount);

    void createInterestAccountFlow(Account bidAccount, BigDecimal interest);
}
