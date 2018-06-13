package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

public class RechargeOfflineQueryObject extends QueryObject{
    private int state=-1;
    private Long bankInfoId=-1L;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String tradeCode;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getBankInfoId() {
        return bankInfoId;
    }

    public void setBankInfoId(Long bankInfoId) {
        this.bankInfoId = bankInfoId;
    }

    public Date getBeginDate() {
        return DateUtil.getBeginDate(beginDate);
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode)?tradeCode.trim():null;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
}
