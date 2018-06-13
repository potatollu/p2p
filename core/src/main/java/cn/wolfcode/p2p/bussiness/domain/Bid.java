package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标对象
 */
public class Bid extends BaseDomain {
    private BigDecimal actualRate;//实际年利率
    private BigDecimal availableAmount;//投标有效金额
    private Long bidRequestId;//来自哪个借款标
    private String bidRequestTitle;//标名称
    private LoginInfo bidUser;//投标人
    private Date bidTime;//投标时间
    private int bidRequestState;//标状态

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public String getBidRequestTitle() {
        return bidRequestTitle;
    }

    public void setBidRequestTitle(String bidRequestTitle) {
        this.bidRequestTitle = bidRequestTitle;
    }

    public LoginInfo getBidUser() {
        return bidUser;
    }

    public void setBidUser(LoginInfo bidUser) {
        this.bidUser = bidUser;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }
}
