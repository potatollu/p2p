package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;

import java.math.BigDecimal;

/**
 * 首页数据汇总对象
 */
public class IndexTotalData extends BaseDomain {
    private int totalBids;//总投资人数
    private int totalBidRequest;//总借款人数
    private BigDecimal totalRequestAmount;//总借款金额
    private BigDecimal totalRewardInterest;//已经赚取总利息
    private BigDecimal totalRemainInterest;//总待收本息

    public int getTotalBids() {
        return totalBids;
    }

    public void setTotalBids(int totalBids) {
        this.totalBids = totalBids;
    }

    public int getTotalBidRequest() {
        return totalBidRequest;
    }

    public void setTotalBidRequest(int totalBidRequest) {
        this.totalBidRequest = totalBidRequest;
    }

    public BigDecimal getTotalRequestAmount() {
        return totalRequestAmount;
    }

    public void setTotalRequestAmount(BigDecimal totalRequestAmount) {
        this.totalRequestAmount = totalRequestAmount;
    }

    public BigDecimal getTotalRewardInterest() {
        return totalRewardInterest;
    }

    public void setTotalRewardInterest(BigDecimal totalRewardInterest) {
        this.totalRewardInterest = totalRewardInterest;
    }

    public BigDecimal getTotalRemainInterest() {
        return totalRemainInterest;
    }

    public void setTotalRemainInterest(BigDecimal totalRemainInterest) {
        this.totalRemainInterest = totalRemainInterest;
    }
}
