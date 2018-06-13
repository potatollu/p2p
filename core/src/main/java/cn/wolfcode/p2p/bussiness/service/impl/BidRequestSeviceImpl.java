package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.event.BidRequstFaileEvent;
import cn.wolfcode.p2p.base.event.BidRequstSuccessEvent;
import cn.wolfcode.p2p.base.event.BidSuccessEvent;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.bussiness.domain.*;
import cn.wolfcode.p2p.bussiness.mapper.BidRequestMapper;
import cn.wolfcode.p2p.bussiness.service.*;
import cn.wolfcode.p2p.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
@Transactional
public class BidRequestSeviceImpl implements IBidRequestSevice {
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBidRequestAuditHistoryService historyService;
    @Autowired
    private IBidService bidService;
    @Autowired
    private IAccountFlowService accountFlowService;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;
    @Autowired
    private ISystemAccountService systemAccountService;
    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;
    @Autowired
    private IPaymentScheduleDetailService detailService;
    @Autowired
    private ApplicationContext cxt;

    /**
     * 保存借款信息
     *
     * @param br
     */
    public void apply(BidRequest br) {
        //判断参数
        //借款金额最小判断
        if (br.getBidRequestAmount().compareTo(Constants.BORROW_MIN_AMOUNT) < 0) {
            throw new DisplayableException("借款金额小于2000.00");
        }
        //借款最大金额
        LoginInfo loginInfo = UserContext.getLoginInfo();
        Account account = accountMapper.selectByPrimaryKey(loginInfo.getId());
        if (br.getBidRequestAmount().compareTo(account.getBorrowLimitAmount()) > 0) {
            throw new DisplayableException("借款金额大于用户所有金额");
        }
        //借款最小利润
        if (br.getCurrentRate().compareTo(Constants.BORROW_MIN_RATE) < 0) {
            throw new DisplayableException("借款利润小于5.00");
        }
        //借款最大利润
        if (br.getCurrentRate().compareTo(Constants.BORROW_MAX_RATE) > 0) {
            throw new DisplayableException("借款利润大于20.00");
        }
        //最小投标下限
        if (br.getMinBidAmount().compareTo(Constants.BID_MIN_AMOUNT) < 0) {
            throw new DisplayableException("投标金额小于50.00");
        }

        //判断用户是有已近有了一个借款,有则throw
        UserInfo userInfo = userInfoService.get(loginInfo.getId());
        if (userInfo.isBidRequestPross()) {
            throw new DisplayableException("您已有一个申请流程");
        }
        //没有,保存

        BidRequest bidRequest = new BidRequest();
        bidRequest.setApplyTime(new Date());
        bidRequest.setBidRequestAmount(br.getBidRequestAmount());
        bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_APPLY);
        bidRequest.setBidRequestType(br.getBidRequestType());
        bidRequest.setCreateUser(loginInfo);
        bidRequest.setCurrentRate(br.getCurrentRate());
        bidRequest.setDescription(br.getDescription());
        bidRequest.setDisableDate(br.getDisableDate());
        bidRequest.setMinBidAmount(br.getMinBidAmount());
        bidRequest.setMonthes2Return(br.getMonthes2Return());
        bidRequest.setTitle(br.getTitle());
        bidRequest.setTotalRewardAmount(
                CalculatetUtil.calTotalInterest(bidRequest.getReturnType(),
                        bidRequest.getBidRequestAmount(),
                        bidRequest.getCurrentRate(),
                        bidRequest.getMonthes2Return())
        );

        bidRequestMapper.insert(bidRequest);

        Long tempState = BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfo.setBitState(tempState);
        userInfoService.update(userInfo);


    }

    public PageInfo queryPage(BidRequestQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<BidRequest> list = bidRequestMapper.queryPage(qo);
        return new PageInfo(list);
    }

    /**
     * 借款审核
     *
     * @param id
     * @param state
     * @param publishTime
     * @param remark
     */
    public void publishAudit(Long id, int state, Date publishTime, String remark) {
        //判断对象是否处于待审核状态
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(id);
        if (bidRequest.getBidRequestState() != Constants.BIDREQUEST_STATE_APPLY) {
            throw new DisplayableException("该用户不是待审核状态");
        }
        //设置审核相关信息                  状态为发标审核
        historyService.save(BidRequestAuditHistory.TYPE_PUBLISH, bidRequest, remark, state);
        //审核通过
        if (state == BidRequestAuditHistory.STATE_SUCCESS) {
            //如果没指定时间,就立即发标,把状态设为招标中
            if (publishTime == null) {
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
                bidRequest.setPublishTime(new Date());
            } else {
                //如果指定发表日期,就将状态改为待发标
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
                bidRequest.setPublishTime(publishTime);
            }
            //招标截止时间
            bidRequest.setDisableDate(DateUtils.addDays(bidRequest.getPublishTime(), bidRequest.getDisableDays()));

            //Date date = new Date();
            //bidRequest.setDisableDays(date.getDay() - publishTime.getDay());
            cxt.publishEvent(new BidRequstSuccessEvent(this,bidRequest));
        } else {
            //审核失败
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_REFUSE);
            //移除用户借款申请流程中的状态
            UserInfo userInfo = userInfoService.get(bidRequest.getCreateUser().getId());
            Long removeState = BitStatesUtils.removeState(userInfo.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfo.setBitState(removeState);
            userInfoService.update(userInfo);
            //2.把消息发布到容器中
            //	当借款失败:发送一个借款失败 站内消息/短信 ...
            cxt.publishEvent(new BidRequstFaileEvent(this,bidRequest));
        }
        update(bidRequest);
    }

    public void update(BidRequest bidRequest) {
        int i = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if (i == 0) {
            throw new DisplayableException("br乐观锁异常");
        }
    }


    public PageResult queryForList(BidRequestQueryObject qo) {
        int i = bidRequestMapper.queryForCount(qo);
        if (i == 0) {
            PageResult.empty(qo.getPageSize());
        }
        List<BidRequest> list = bidRequestMapper.queryForList(qo);
        return new PageResult(list, i, qo.getCurrentPage(), qo.getPageSize());
    }

    /**
     * 每个多少秒调用该方法一次
     * 该方法用于监控发标时间
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void bidRequestPublishCheck() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        //设置为待发布状态
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        //查询待发布的借款
        List<BidRequest> requestList = bidRequestMapper.queryForList(qo);
        Date now = new Date();
        //迭代遍历每一条对象
        for (int i = 0; i < requestList.size(); i++) {
            BidRequest bidRequest = requestList.get(i);
            //判断放标时间到了没有
            if (now.after(bidRequest.getPublishTime())) {
                //到就放标
                bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_BIDDING);
                update(bidRequest);
            }
        }

    }

    public BidRequest get(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    /**
     * 投标
     *
     * @param
     * @return
     */
    public void bid(Long bidRequestId, BigDecimal amount) {
        //判断借款的状态必须处于招标中,根据这个标的id查询数据库有没有
        BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        if (bidRequest == null) {
            throw new DisplayableException("无效的借款");
        }
        //借款本人不能投标:当前登录用户和标的借款人对比
        LoginInfo currentUser = UserContext.getLoginInfo();
        if (currentUser.getId().equals(bidRequest.getCreateUser().getId())) {
            throw new DisplayableException("不能投自己的借款");
        }
        //投的金额>可用金额
        Account bidAccount = accountService.get(currentUser.getId());
        if (amount.compareTo(bidAccount.getUsableAmount()) > 0) {
            throw new DisplayableException("最多可投:" + bidRequest.getRemainAmount());
        }
        //借款余额<最小投标金额,允许一次性投完
        if (bidRequest.getRemainAmount().compareTo(bidRequest.getMinBidAmount()) < 0) {
            //允许一次把剩下的标投完
            if (amount.compareTo(bidRequest.getRemainAmount()) != 0) {
                throw new DisplayableException("当前可投金额" + bidRequest.getRemainAmount());
            }
        } else {
            //投资金额不能小于最小投资金额
            if (amount.compareTo(bidRequest.getMinBidAmount()) < 0) {
                throw new DisplayableException("最小投标金额" + bidRequest.getMinBidAmount());
            }
        }
        //一个投资人对于一个标总投资金额不能超过 借款金额的百分之五十
        BigDecimal bidTotalAmount = bidService.sumUserBidAmountByBidRequstId(bidRequest.getId(), currentUser.getId());//投标有效金额
        bidTotalAmount = bidTotalAmount.add(amount);
        BigDecimal tempAmount = bidRequest.getBidRequestAmount().multiply(new BigDecimal("0.5"));//借款金额的50%
        if (bidTotalAmount.compareTo(tempAmount) > 0) {
            throw new DisplayableException("投标金额不能超过" + tempAmount);
        }
        //执行投标
        //保存投标记录

        bidService.save(bidRequest, amount, currentUser);
        //投资人可用余额减少
        Account account = accountService.get(currentUser.getId());
        account.setUsableAmount(account.getUsableAmount().subtract(amount));
        //冻结金额增加
        account.setFreezedAmount(account.getFreezedAmount().add(amount));
        accountService.update(account);

        //参数冻结流水
        accountFlowService.createBidFlow(account, amount);
        //借款对象投标总数增加
        bidRequest.setBidCount(bidRequest.getBidCount() + 1);
        //           当前已有投资金额+amount
        bidRequest.setCurrentSum(bidRequest.getCurrentSum().add(amount));
        //投标总金额增加
        if (bidRequest.getCurrentSum().compareTo(bidRequest.getBidRequestAmount()) == 0) {
            //如果以投满
            //借款表现状态修改为满标一审
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
            //该将诶款对象下面的投标对象的状态修改为满标一审
            bidService.batchUpdateState(bidRequest.getId(), Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        }
        update(bidRequest);
        cxt.publishEvent(new BidSuccessEvent(this,currentUser));
    }

    /**
     * 满标一审
     *
     * @param id
     * @param state
     * @param remark
     */
    public void audit(Long id, int state, String remark) {
        //判断用户是否在处于满标一审状态
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        if (br.getBidRequestState() != Constants.BIDREQUEST_STATE_APPROVE_PENDING_1) {
            throw new DisplayableException("该用户不在满标一审状态");
        }
        //设置审核信息
        historyService.save(BidRequestAuditHistory.TYPE_AUDIT1, br, remark, state);
        //审核成功
        //修改状态为满标二审
        if (state == BidRequestAuditHistory.STATE_SUCCESS) {
            br.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
            //借款下的投资对象都改为满标二审
            bidService.batchUpdateState(br.getId(), Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        } else {
            fullAuditReject(br);

        }
        update(br);
    }

    /**
     * 审核失败
     *
     * @param br
     */
    private void fullAuditReject(BidRequest br) {
        //审核失败:
        //修改状态为满标审核拒绝
        br.setBidRequestState(Constants.BIDREQUEST_STATE_REJECTED);
        bidService.batchUpdateState(br.getId(), Constants.BIDREQUEST_STATE_REJECTED);
        //获得所有投标对象,把钱还给他们
        List<Bid> bids = bidService.getBids();

        //投资人账户缓存
        Map<Long, Account> bidAccounts = new HashMap<Long, Account>();

        for (int i = 0; i < bids.size(); i++) {
            Bid bid = bids.get(i);
            //投资人id
            Long bidId = bid.getBidUser().getId();
            Account bidAccount = bidAccounts.get(bidId);
            if (bidAccount == null) {
                //投资人账户
                bidAccount = accountService.get(bidId);
                bidAccounts.put(bidId, bidAccount);
            }

            //审核拒绝:投资人可用金额增加,冻结资金减少
            //availableAmount投标有效金额
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(bid.getAvailableAmount()));
            bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
            //产生解冻流水
            accountFlowService.createAuditErrorFlow(bidAccount, bid.getAvailableAmount());
        }
        //保存每个投资人账户信息
        Collection<Account> accounts = bidAccounts.values();
        for (Account account : accounts) {
            accountService.update(account);
        }
        //修改位状态
        //移除用户申请中的借款状态
        UserInfo userInfo = userInfoService.get(br.getCreateUser().getId());
        Long tempState = BitStatesUtils.removeState(userInfo.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfo.setBitState(tempState);
        userInfoService.update(userInfo);

    }

    /**
     * 满标二审
     *
     * @param id
     * @param state
     * @param remark
     */
    public void audit2(Long id, int state, String remark) {
        //判断申请对象是否在满标二审状态
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        if (br.getBidRequestState() != Constants.BIDREQUEST_STATE_APPROVE_PENDING_2) {
            throw new DisplayableException("不在满标二审状态");
        }
        //设置审核相关信息
        historyService.save(BidRequestAuditHistory.TYPE_AUDIT2, br, remark, state);
        //审核成功
        if (state == BaseAuditDomain.STATE_SUCCESS) {
            //设置状态为还款中
            br.setBidRequestState(Constants.BIDREQUEST_STATE_PAYING_BACK);
            //该借款下的投标对象修改为还款中
            bidService.batchUpdateState(br.getId(), Constants.BIDREQUEST_STATE_PAYING_BACK);

            //借款人的账户
            Account borrowAccount = accountService.get(br.getCreateUser().getId());
            //可用金额+收到借款金额
            borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().add(br.getBidRequestAmount()));
            //增加收款流水
            accountFlowService.creatBorrowSuccess(borrowAccount, br.getBidRequestAmount());
            //授信额度减少
            borrowAccount.setRemainBorrowLimit(borrowAccount.getBorrowLimitAmount().subtract(br.getBidRequestAmount()));
            //待还总额增加:借款金额+利息
            borrowAccount.setUnReturnAmount(borrowAccount.getUnReturnAmount().add(br.getBidRequestAmount()).add(br.getTotalRewardAmount()));
            //移除借款人借款状态
            UserInfo borrowUserInfo = userInfoService.get(br.getCreateUser().getId());
            Long tempState = BitStatesUtils.removeState(borrowUserInfo.getBitState(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            borrowUserInfo.setBitState(tempState);
            userInfoService.update(borrowUserInfo);
            //给平台付借款手续 可用余额减少
            BigDecimal managementCharge = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            borrowAccount.setUsableAmount(borrowAccount.getUsableAmount().subtract(managementCharge));
            accountService.update(borrowAccount);
            //产生支付流水
            accountFlowService.creatManagementChargeFlow(borrowAccount, managementCharge);

            //修改投资人账户
            Map<Long, Account> bidAccounts = new HashMap<Long, Account>();
            List<Bid> bids = bidService.getBids();
            for (int i = 0; i < bids.size(); i++) {
                Bid bid = bids.get(i);
                Long userId = bid.getBidUser().getId();
                Account bidAccount = bidAccounts.get(userId);
                if (bidAccount == null) {
                    bidAccount = accountService.get(userId);
                    bidAccounts.put(userId, bidAccount);
                }
                //投资人冻结金额减少
                bidAccount.setFreezedAmount(bidAccount.getFreezedAmount().subtract(bid.getAvailableAmount()));
                //投标成功,产生解冻流水
                accountFlowService.createUnFreedzedFlowAmount(bidAccount, bid.getAvailableAmount());
            }
               //待收本金增加
            List<PaymentSchedule> pss = paymentScheduleService.createPaymentSchedule(br);
            for (PaymentSchedule ps : pss) {
                List<PaymentScheduleDetail> details = ps.getDetails();
                for (PaymentScheduleDetail detail : details) {
                    Account account = bidAccounts.get(detail.getToLoginInfoId());
                    account.setUnReceivePrincipal(account.getUnReceivePrincipal().add(detail.getPrincipal()));
                    account.setUnReceiveInterest(detail.getInterest().add(detail.getInterest()));
                }
            }

            Collection<Account> accounts = bidAccounts.values();
            for (Account account : accounts) {
                accountService.update(account);
            }
            //生成还款/收款计划
            //平台账户:
            SystemAccount systemAccount = systemAccountService.getCurrent();
            //收到借款人的借款管理费
            systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(managementCharge));
            //产生平台管理费收取流水
            systemAccountFlowService.systemManagementChargeFlow(systemAccount, managementCharge);
            systemAccountService.update(systemAccount);
        } else {
            //审核失败
            //同一审
            fullAuditReject(br);
        }
        update(br);
    }

    /**
     * 还款
     *
     * @param id
     */
    public void returnMone(Long id) {
        //判断还款计划的状态处于待还款状态
        PaymentSchedule ps = paymentScheduleService.get(id);
        if (ps.getState() != Constants.PAYMENT_STATE_NORMAL) {
            throw new DisplayableException("该对象不在还款状态");
        }
        LoginInfo loginInfo = UserContext.getLoginInfo();
        //当前登录用户必须是借款人
        if (loginInfo.getId() != ps.getCreateUserId()) {
            throw new DisplayableException("当前登录用户不是借款人");
        }
        //借款人余额>还款金额
        Account returnAccont = accountService.get(loginInfo.getId());
        if (returnAccont.getUsableAmount().compareTo(ps.getTotalAmount()) < 0) {
            throw new DisplayableException("您的账户余额小于还款金额");
        }
        //执行还款
        //还款人
        //减少可用余额
        returnAccont.setUsableAmount(returnAccont.getUsableAmount().subtract(ps.getTotalAmount()));
        //增加管理流水
        accountFlowService.createReturnAccountFlow(returnAccont, ps.getTotalAmount());
        //增加授信余额
        returnAccont.setRemainBorrowLimit(returnAccont.getRemainBorrowLimit().add(ps.getPrincipal()));
        //待还总额减少
        returnAccont.setUnReturnAmount(returnAccont.getUnReturnAmount().subtract(ps.getTotalAmount()));
        accountService.update(returnAccont);

        //还款计划
        //修改还款时间
        Date now = new Date();
        ps.setPayDate(now);
        //修改状态
        ps.setState(Constants.PAYMENT_STATE_DONE);

        paymentScheduleService.update(ps);

        List<PaymentScheduleDetail> details = ps.getDetails();
        Map<Long, Account> bidAccounts = new HashMap<Long, Account>();

        //平台:
        SystemAccount systemAccount = systemAccountService.getCurrent();

        for (int i = 0; i < details.size(); i++) {
            //收款计划
            PaymentScheduleDetail detail = details.get(i);
            //修改收款时间
            detail.setPayDate(now);
            detailService.update(detail);

            //收款人
            //修改状态
            Long bidUserId = detail.getToLoginInfoId();
            Account bidAccount = bidAccounts.get(bidUserId);
            if (bidAccount == null) {
                bidAccount = accountService.get(bidUserId);
                bidAccounts.put(bidUserId, bidAccount);
            }
            //可用余额增加
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().add(detail.getTotalAmount()));
            //创建收款流水
            accountFlowService.createGetmoneyAccountFllow(bidAccount, ps.getTotalAmount());
            //待收本金减少
            bidAccount.setUnReceivePrincipal(bidAccount.getUnReceivePrincipal().subtract(detail.getPrincipal()));
            //待收利息减少
            bidAccount.setUnReceiveInterest(bidAccount.getUnReceiveInterest().subtract(detail.getInterest()));
            //支付利息管理费
            BigDecimal interest = CalculatetUtil.calInterestManagerCharge(detail.getInterest());
            bidAccount.setUsableAmount(bidAccount.getUsableAmount().subtract(interest));
            //生产利息流水
            accountFlowService.createInterestAccountFlow(bidAccount, interest);


            //收取利息管理费:可以金额增加
            systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interest));
            //产生利息管理费流水
            systemAccountFlowService.createInterestManegerFlow(systemAccount, interest);
        }
        //修改系统信息
        systemAccountService.update(systemAccount);

        Collection<Account> accounts = bidAccounts.values();
        for (Account account : accounts) {
            accountService.update(account);
        }
        //如果已还清
        //借款状态改为已还清
        List<PaymentSchedule> pss = paymentScheduleService.listByBidRequestid(ps.getBidRequestId());
        //借款状态修改为已还满
        boolean success = true;
        for (int i = 0; i < pss.size(); i++) {
            PaymentSchedule tempPs = pss.get(i);
            if (tempPs.getState() != Constants.PAYMENT_STATE_DONE) {
                success = false;
                break;
            }
        }
        if (success) {
            //借款状态修改为已还满
            BidRequest bidRequest = bidRequestMapper.selectByPrimaryKey(ps.getBidRequestId());
            bidRequest.setBidRequestState(Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            update(bidRequest);
            //次借款下的投标对象状态修改为已还清
            bidService.batchUpdateState(bidRequest.getId(), Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
        }
    }

}





