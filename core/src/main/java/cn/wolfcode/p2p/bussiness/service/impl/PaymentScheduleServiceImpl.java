package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.bussiness.domain.Bid;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.domain.PaymentSchedule;
import cn.wolfcode.p2p.bussiness.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.bussiness.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.bussiness.mapper.PaymentScheduleMapper;
import cn.wolfcode.p2p.bussiness.service.IPaymentScheduleService;
import cn.wolfcode.p2p.util.CalculatetUtil;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.PageResult;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

    /**
     * 借款人:生成还款计划
     * @param br
     */
    public List<PaymentSchedule> createPaymentSchedule(BidRequest br) {
        Date beginDate = br.getPublishTime();
        List<PaymentSchedule> pss = new ArrayList<PaymentSchedule>();
        int monthes2Return = br.getMonthes2Return();
        BigDecimal totalPrincipal = Constants.ZERO;
        BigDecimal totalInterest = Constants.ZERO;
        for (int i = 0; i < monthes2Return ; i++) {
            PaymentSchedule ps = new PaymentSchedule();
            ps.setBidRequestId(br.getId());
            ps.setBidRequestTitle(br.getTitle());
            ps.setBidRequestType(br.getBidRequestType());
            ps.setCreateUserId(br.getCreateUser().getId());
            int mothIndex = i+1;
            //还款截止日期
            ps.setDeadLine(DateUtils.addMonths(beginDate,mothIndex));
            ps.setMonthIndex(mothIndex);//第几期
            ps.setReturnType(br.getReturnType());
            ps.setState(Constants.PAYMENT_STATE_NORMAL);//正常待还

            if (i == br.getMonthes2Return() - 1){
                ps.setInterest(br.getTotalRewardAmount().subtract(totalInterest));
                ps.setPrincipal(br.getBidRequestAmount().subtract(totalPrincipal));
                ps.setTotalAmount(ps.getPrincipal().add(ps.getInterest()));
            }else {
                //本期还款总额
                BigDecimal totalAmount = CalculatetUtil.calMonthToReturnMoney(br.getReturnType(), br.getBidRequestAmount(),
                        br.getCurrentRate(), mothIndex, monthes2Return);
                ps.setTotalAmount(totalAmount.setScale(Constants.SCALE_STORE, RoundingMode.HALF_UP));
                //本期还款利息
                BigDecimal interest = CalculatetUtil.calMonthlyInterest(br.getReturnType(), br.getBidRequestAmount(), br.getCurrentRate(), mothIndex, monthes2Return);
                ps.setInterest(interest.setScale(Constants.SCALE_STORE,RoundingMode.HALF_UP));
                //本期还款本金
                ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));
                totalPrincipal = totalPrincipal.add(ps.getPrincipal());
                totalInterest = totalPrincipal.add(ps.getInterest());
            }
            paymentScheduleMapper.insert(ps);
            //创建收款计划
            createDetail(ps,br);
            pss.add(ps);
        }
        return pss;
    }

    /**
     * 分页
     * @param qo
     * @return
     */
    public PageResult query(PaymentScheduleQueryObject qo) {
        int i = paymentScheduleMapper.queryForCount(qo);
        if (i == 0) {
            PageResult.empty(qo.getPageSize());
        }
        List<PaymentSchedule> list = paymentScheduleMapper.queryForList(qo);
        return new PageResult(list,i,qo.getCurrentPage(),qo.getPageSize());
    }

    /**
     * 根据id获取还款计划对象
     * @param id
     * @return
     */
    public PaymentSchedule get(Long id) {
        return paymentScheduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PaymentSchedule> listByBidRequestid(Long bidRequestId) {
        return paymentScheduleMapper.listByBidRequestid(bidRequestId);
    }

    @Override
    public void update(PaymentSchedule ps) {
        int i = paymentScheduleMapper.updateByPrimaryKey(ps);
        if (i==0){
            throw new DisplayableException("还款计划修改失败");
        }
    }

    /**
     * 投资人:生成收款计划
     * @param ps
     * @param br
     */
    private void createDetail(PaymentSchedule ps, BidRequest br) {
        List<Bid> bids = br.getBids();
        BigDecimal totalPrincipal = Constants.ZERO;
        BigDecimal totalInterest = Constants.ZERO;
        for (int i = 0; i < bids.size(); i++) {
            Bid bid = bids.get(i);
            PaymentScheduleDetail detail = new PaymentScheduleDetail();
            detail.setBidAmount(bid.getAvailableAmount());
            detail.setBidId(bid.getId());
            detail.setBidRequestId(br.getId());
            detail.setDeadline(ps.getDeadLine());
            detail.setFromLoginInfo(br.getCreateUser());//还款人(即发标人)
            detail.setToLoginInfoId(bid.getBidUser().getId());// 收款人(即投标人)
            detail.setMonthIndex(ps.getMonthIndex());
            detail.setPaymentScheduleId(ps.getId());
            detail.setReturnType(br.getReturnType());

            if(i == bids.size() - 1){
                detail.setInterest(ps.getInterest().subtract(totalInterest));
                detail.setPrincipal(ps.getPrincipal().subtract(totalPrincipal));
                detail.setTotalAmount(detail.getInterest().add(ps.getPrincipal()));
            }else {
                //收款本金=bid投标金额/总借款金额*还款计划的本期本金
                BigDecimal divide = bid.getAvailableAmount().divide(br.getBidRequestAmount(),Constants.SCALE_STORE, RoundingMode.HALF_UP);
                detail.setPrincipal(divide.multiply(ps.getPrincipal()).setScale(Constants.SCALE_STORE,RoundingMode.HALF_UP));
                detail.setInterest(divide.multiply(ps.getInterest()).setScale(Constants.SCALE_STORE,RoundingMode.HALF_UP));
                detail.setTotalAmount(detail.getPrincipal().add(detail.getInterest()));
                totalPrincipal = totalPrincipal.add(detail.getInterest());
                totalInterest = totalInterest.add(detail.getInterest());

            }

            paymentScheduleDetailMapper.insert(detail);
            ps.getDetails().add(detail);
        }
    }
}













