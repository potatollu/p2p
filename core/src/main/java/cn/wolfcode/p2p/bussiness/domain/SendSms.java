package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;

public class SendSms extends BaseDomain {
    private String bidRequstSuccess;
    private String bidRequstFaile;
    private String bidSuccess;
    private Long userId;
    private Long state;

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBidRequstSuccess() {
        return bidRequstSuccess;
    }

    public void setBidRequstSuccess(String bidRequstSuccess) {
        this.bidRequstSuccess = bidRequstSuccess;
    }

    public String getBidRequstFaile() {
        return bidRequstFaile;
    }

    public void setBidRequstFaile(String bidRequstFaile) {
        this.bidRequstFaile = bidRequstFaile;
    }

    public String getBidSuccess() {
        return bidSuccess;
    }

    public void setBidSuccess(String bidSuccess) {
        this.bidSuccess = bidSuccess;
    }
}
