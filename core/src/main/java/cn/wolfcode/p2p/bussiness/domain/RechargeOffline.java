package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseAuditDomain;
import com.alibaba.fastjson.JSON;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线下充值对象
 */
public class RechargeOffline extends BaseAuditDomain{
    private String tradeCode;//充值流水号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;//充值时间
    private BigDecimal amount;//充值金额
    private String note;//备注
    private PlatformBankInfo bankInfo;//银行



    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",applier.getUsername());
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        map.put("tradeTime",tradeTime);
        return JSON.toJSONString(map);
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PlatformBankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(PlatformBankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }
}
