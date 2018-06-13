package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.Constants;

import java.math.BigDecimal;

/**
 * 用户账户信息
 */
public class Account extends BaseDomain {
    //版本
    private int version;
    //交易密码
    private String tradePassword;
    //可用余额
    private BigDecimal usableAmount = Constants.ZERO;
    //冻结金额
    private BigDecimal freezedAmount = Constants.ZERO;
    //代收利息
    private BigDecimal unReceiveInterest = Constants.ZERO;
    //待收本金
    private BigDecimal unReceivePrincipal = Constants.ZERO;
    //待还总额
    private BigDecimal unReturnAmount = Constants.ZERO;
    //授信额度
    private BigDecimal borrowLimitAmount = Constants.BORROW_LIMIT;
    //剩余可用授信额度
    private BigDecimal remainBorrowLimit = Constants.BORROW_LIMIT;

    //总额
    public BigDecimal getTotalAmount(){
        BigDecimal total = usableAmount.add(freezedAmount).add(unReceivePrincipal);
        return total;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getFreezedAmount() {
        return freezedAmount;
    }

    public void setFreezedAmount(BigDecimal freezedAmount) {
        this.freezedAmount = freezedAmount;
    }

    public BigDecimal getUnReceiveInterest() {
        return unReceiveInterest;
    }

    public void setUnReceiveInterest(BigDecimal unReceiveInterest) {
        this.unReceiveInterest = unReceiveInterest;
    }

    public BigDecimal getUnReceivePrincipal() {
        return unReceivePrincipal;
    }

    public void setUnReceivePrincipal(BigDecimal unReceivePrincipal) {
        this.unReceivePrincipal = unReceivePrincipal;
    }

    public BigDecimal getUnReturnAmount() {
        return unReturnAmount;
    }

    public void setUnReturnAmount(BigDecimal unReturnAmount) {
        this.unReturnAmount = unReturnAmount;
    }

    public BigDecimal getBorrowLimitAmount() {
        return borrowLimitAmount;
    }

    public void setBorrowLimitAmount(BigDecimal borrowLimitAmount) {
        this.borrowLimitAmount = borrowLimitAmount;
    }

    public BigDecimal getRemainBorrowLimit() {
        return remainBorrowLimit;
    }

    public void setRemainBorrowLimit(BigDecimal remainBorrowLimit) {
        this.remainBorrowLimit = remainBorrowLimit;
    }
}

