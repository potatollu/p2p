package cn.wolfcode.p2p.base.vo;

import java.util.Date;

public class VerifyCodeVO {
    private String code;
    private String phoneNumber;
    private Date sendTime;

    public VerifyCodeVO(String code, String phoneNumber, Date sendTime) {
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.sendTime = sendTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
