package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 还款计划对象
 */
public class PaymentSchedule extends BaseDomain {

    /**
     * 期数
     */
    private int monthIndex;

    /**
     * 当期还款截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    private Date deadLine;

    /**
     * 还款人真实还款时间
     */
    private Date payDate;

    /**
     * 本期还款总额
     */
    private BigDecimal totalAmount;

    /**
     * 本期还款本金
     */
    private BigDecimal principal;

    /**
     * 本期还款利息
     */
    private BigDecimal interest;

    /**
     * 本期还款状态
     */
    private int state;

    /**
     * 借款类型
     */
    private int bidRequestType;

    /**
     * 还款方式
     */
    private int returnType;

    /**
     * 对应的借款
     */
    private Long bidRequestId;

    /**
     * 还款人
     */
    private Long createUserId;

    /**
     * 借款标题
     */
    private String bidRequestTitle;

    /**
     * 还款计划对应的收款计划
     */
    private List<PaymentScheduleDetail> details = new ArrayList<PaymentScheduleDetail>();

    public String getStateDisplay(){
        switch (state){
            case Constants.PAYMENT_STATE_NORMAL: return "待还款";
            case Constants.PAYMENT_STATE_DONE :return "已还款";
            case Constants.PAYMENT_STATE_OVERDUE :return "逾期";
        }
        return null;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getBidRequestType() {
        return bidRequestType;
    }

    public void setBidRequestType(int bidRequestType) {
        this.bidRequestType = bidRequestType;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getBidRequestTitle() {
        return bidRequestTitle;
    }

    public void setBidRequestTitle(String bidRequestTitle) {
        this.bidRequestTitle = bidRequestTitle;
    }

    public List<PaymentScheduleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PaymentScheduleDetail> details) {
        this.details = details;
    }
}
