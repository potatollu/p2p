package cn.wolfcode.p2p.base.domain;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BaseAuditDomain extends BaseDomain{
    //审核状态
    public static final int STATE_NOMAL=0;//待审核
    public static final int STATE_SUCCESS=1;//审核成功
    public static final int STATE_FAIL=2;//审核失败

    //审核相关
    protected int state;//审核状态
    protected LoginInfo applier;//申请人
    protected LoginInfo auditor;//审核人
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date applyTime;//申请时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date auditTime;//审核时间
    protected String remark;//备注

    public String getStateDisplay(){
        switch (state){
            case 0:return "待审核";
            case 1:return "审核通过";
            case 2:return "审核失败";
        }
        return "";
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LoginInfo getApplier() {
        return applier;
    }

    public void setApplier(LoginInfo applier) {
        this.applier = applier;
    }

    public LoginInfo getAuditor() {
        return auditor;
    }

    public void setAuditor(LoginInfo auditor) {
        this.auditor = auditor;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
