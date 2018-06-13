package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 针对一个还款计划,投资人的回款明细
 * 
 * @author Administrator
 * 
 */
public class PaymentScheduleDetail extends BaseDomain {

	// 该投标人总共投标金额,便于还款/垫付查询
	private BigDecimal bidAmount;

	// 对应的投标ID
	private Long bidId;

	// 本期还款总金额(=本金+利息)
	private BigDecimal totalAmount = Constants.ZERO;

	// 本期应还款本金
	private BigDecimal principal = Constants.ZERO;

	// 本期应还款利息
	private BigDecimal interest = Constants.ZERO;

	// 第几期（即第几个月）
	private int monthIndex;

	// 本期还款截止时间
	private Date deadline;

	// 所属哪个借款
	private Long bidRequestId;

	// 实际付款日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDate;

	// 还款方式
	private int returnType;

	// 所属还款计划
	private Long paymentScheduleId;

	// 还款人(即发标人)
	private LoginInfo fromLoginInfo;

	// 收款人(即投标人)
	private Long toLoginInfoId;

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
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

	public int getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(int monthIndex) {
		this.monthIndex = monthIndex;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Long getBidRequestId() {
		return bidRequestId;
	}

	public void setBidRequestId(Long bidRequestId) {
		this.bidRequestId = bidRequestId;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public Long getPaymentScheduleId() {
		return paymentScheduleId;
	}

	public void setPaymentScheduleId(Long paymentScheduleId) {
		this.paymentScheduleId = paymentScheduleId;
	}

	public LoginInfo getFromLoginInfo() {
		return fromLoginInfo;
	}

	public void setFromLoginInfo(LoginInfo fromLoginInfo) {
		this.fromLoginInfo = fromLoginInfo;
	}

	public Long getToLoginInfoId() {
		return toLoginInfoId;
	}

	public void setToLoginInfoId(Long toLoginInfoId) {
		this.toLoginInfoId = toLoginInfoId;
	}
}
