package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseDomain;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.bussiness.domain.Bid;
import cn.wolfcode.p2p.util.Constants;
import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 借款对象
 */
public class BidRequest extends BaseDomain {
    private int version;//版本
    private int returnType;//还款方式
    private int bidRequestType;//借款类型
    private int bidRequestState;//借款状态
    private BigDecimal bidRequestAmount;//借款金额
    private BigDecimal currentRate;//借款利率
    private BigDecimal minBidAmount;//最小投资金额
    private int monthes2Return;//还款期限
    private int bidCount;//投资次数
    private BigDecimal totalRewardAmount;//总回报金额
    private BigDecimal currentSum = Constants.ZERO;//当前已有投资金额
    private String title;//借款标题
    private String description;//借款描述
    private String note;//风控审批意见
    private Date disableDate;//招标到期时间
    private int disableDays;//招标天数
    private LoginInfo createUser;//借款人
    private List<Bid> bids = new ArrayList<Bid>();//投标记录
    private Date applyTime;//申请时间
    private Date publishTime;//发布时间

    public BigDecimal getRemainAmount(){
        return bidRequestAmount.subtract(this.getCurrentSum());
    }

    //投标进度
    public int getPersent(){
        return  ((getCurrentSum().divide(getBidRequestAmount(),Constants.SCALE_CAL, RoundingMode.HALF_UP))
                .multiply(new BigDecimal("100.00"))).intValue();
    }

    public String getReturnTypeDisplay(){
        return returnType==Constants.RETURN_TYPE_MONTH_INTEREST?"按月到期还款":"按月分期还款";
    }

    public String getBidRequestStateDisplay(){
        switch (bidRequestState){
            case Constants.BIDREQUEST_STATE_APPLY : return "借款申请";
            case Constants.BIDREQUEST_STATE_PUBLISH_PENDING : return "待发布";
            case Constants.BIDREQUEST_STATE_BIDDING : return "招标中";
            case Constants.BIDREQUEST_STATE_UNDO : return "已撤销";
            case Constants.BIDREQUEST_STATE_BIDDING_OVERDUE : return "流标";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_1 : return "满标1审";
            case Constants.BIDREQUEST_STATE_APPROVE_PENDING_2 : return "满标2审";
            case Constants.BIDREQUEST_STATE_REJECTED : return "满标审核被拒绝";
            case Constants.BIDREQUEST_STATE_PAYING_BACK : return "还款中";
            case Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK : return "已还清";
            case Constants.BIDREQUEST_STATE_PAY_BACK_OVERDUE : return "逾期";
            case Constants.BIDREQUEST_STATE_PUBLISH_REFUSE : return "发标审核拒绝状态";
        }
        return null;
    }

    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",createUser.getUsername());
        map.put("title",title);
        map.put("bidRequestAmount",bidRequestAmount);
        map.put("currentRate",currentRate);
        map.put("monthes2Return",monthes2Return);
        map.put("returnType",returnType);
        map.put("totalRewardAmount",totalRewardAmount);
        return JSON.toJSONString(map);
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public int getBidRequestType() {
        return bidRequestType;
    }

    public void setBidRequestType(int bidRequestType) {
        this.bidRequestType = bidRequestType;
    }

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }

    public BigDecimal getBidRequestAmount() {
        return bidRequestAmount;
    }

    public void setBidRequestAmount(BigDecimal bidRequestAmount) {
        this.bidRequestAmount = bidRequestAmount;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public BigDecimal getMinBidAmount() {
        return minBidAmount;
    }

    public void setMinBidAmount(BigDecimal minBidAmount) {
        this.minBidAmount = minBidAmount;
    }

    public int getMonthes2Return() {
        return monthes2Return;
    }

    public void setMonthes2Return(int monthes2Return) {
        this.monthes2Return = monthes2Return;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public BigDecimal getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public void setTotalRewardAmount(BigDecimal totalRewardAmount) {
        this.totalRewardAmount = totalRewardAmount;
    }

    public BigDecimal getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(BigDecimal currentSum) {
        this.currentSum = currentSum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    public int getDisableDays() {
        return disableDays;
    }

    public void setDisableDays(int disableDays) {
        this.disableDays = disableDays;
    }

    public LoginInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(LoginInfo createUser) {
        this.createUser = createUser;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
