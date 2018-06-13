package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.bussiness.domain.AccountFlow;
import cn.wolfcode.p2p.bussiness.mapper.AccountFlowMapper;
import cn.wolfcode.p2p.bussiness.service.IAccountFlowService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class AccountFlowServiceImpl implements IAccountFlowService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    public void createBaseFlow(Account account, int actionType, BigDecimal amount,String note){
        AccountFlow flow = new AccountFlow();
        Date now = new Date();
        flow.setAccountId(account.getId());//余额
        flow.setActionTime(now);
        flow.setActionType(actionType);
        flow.setAmount(amount);//交易金额
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setNote(note);
        flow.setUsableAmount(account.getUsableAmount());
        accountFlowMapper.insert(flow);
    }

    /**
     * 添加充值成功流水
     */
    public void createRechargeAccountFlow(Account account, int actionType, BigDecimal amount) {
        createBaseFlow(account, Constants.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,amount,"线下充值成功:"+amount+"元"+new Date().toLocaleString());
    }

    public void createBidFlow(Account account,  BigDecimal amount) {
        createBaseFlow(account,Constants.ACCOUNT_ACTIONTYPE_BID_FREEZED,amount,"冻结金额:"+amount+"元"+new Date().toLocaleString());
    }

    /**
     * 满标一审错误,取消冻结金额
     */
    public void createAuditErrorFlow(Account account,  BigDecimal amount) {
        createBaseFlow(account,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,amount,
                "满标一审错误,取消冻结金额:"+amount+"元"+new Date().toLocaleString());
    }
    /**
     * 满标二审成功
     */
    public void creatBorrowSuccess(Account account,  BigDecimal amount) {
        createBaseFlow(account,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,amount,
                "借款成功,可用余额增加:"+amount+"元"+new Date().toLocaleString());
    }

    public void creatManagementChargeFlow(Account account,  BigDecimal amount) {
        createBaseFlow(account,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,amount,
                "借款成功,支付管理费:"+amount+"元"+new Date().toLocaleString());
    }

    @Override
    public void createUnFreedzedFlowAmount(Account account,  BigDecimal amount) {
        createBaseFlow(account,Constants.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,amount,
                "投标成功,冻结金额减少:"+amount+"元"+new Date().toLocaleString());
    }

    @Override
    public void createReturnAccountFlow(Account returnAccont, BigDecimal totalAmount) {
        createBaseFlow(returnAccont,Constants.ACCOUNT_ACTIONTYPE_RETURN_MONEY,totalAmount,
                "还款成功:可用余额减少:"+returnAccont+"元"+new Date().toLocaleString());
    }

    @Override
    public void createGetmoneyAccountFllow(Account bidAccount, BigDecimal totalAmount) {
        createBaseFlow(bidAccount,Constants.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY,totalAmount,
                "收款成功:可用余额增加:"+bidAccount+"元"+new Date().toLocaleString());
    }

    @Override
    public void createInterestAccountFlow(Account bidAccount, BigDecimal interest) {
        createBaseFlow(bidAccount,Constants.ACCOUNT_ACTIONTYPE_INTEREST_SHARE,interest,
                "收款成功,支付平台管理费:"+bidAccount+"元"+new Date().toLocaleString());
    }
}
