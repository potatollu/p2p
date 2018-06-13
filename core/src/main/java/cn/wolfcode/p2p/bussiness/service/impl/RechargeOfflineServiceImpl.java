package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BaseAuditDomain;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.bussiness.mapper.RechargeOfflineMapper;
import cn.wolfcode.p2p.bussiness.service.IAccountFlowService;
import cn.wolfcode.p2p.bussiness.service.IRechargeOfflineService;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {

    @Autowired
    private RechargeOfflineMapper offlineMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;


    /**
     * 线下充值
     * @param rechargeOffline
     */
    public void apply(RechargeOffline rechargeOffline) {
        //保存充值申请
        RechargeOffline newRechage = new RechargeOffline();
        newRechage.setAmount(rechargeOffline.getAmount());
        newRechage.setBankInfo(rechargeOffline.getBankInfo());
        newRechage.setNote(rechargeOffline.getNote());
        newRechage.setTradeCode(rechargeOffline.getTradeCode());
        newRechage.setTradeTime(rechargeOffline.getTradeTime());
        newRechage.setApplyTime(new Date());
        newRechage.setApplier(UserContext.getLoginInfo());
        offlineMapper.insert(newRechage);
    }

    /**
     * 分页
     * @param qo
     * @return
     */
    public PageResult query(RechargeOfflineQueryObject qo) {
        int i = offlineMapper.queryForCount(qo);
        if (i==0){
            PageResult.empty(qo.getPageSize());
        }
        List<RechargeOffline> list = offlineMapper.queryForList(qo);
        return new PageResult(list,i,qo.getCurrentPage(),qo.getPageSize());
    }

    /**
     * 线下充值审核按钮
     * @param id
     * @param state
     * @param remark
     */
    public void audit(Long id, int state, String remark) {
        //判断是否在审核状态
        //拿到充值对象
        RechargeOffline rechargeOffline = offlineMapper.selectByPrimaryKey(id);
        if (rechargeOffline.getState() != RechargeOffline.STATE_NOMAL){
            throw new DisplayableException("该用户不在审核状态");
        }
        rechargeOffline.setAuditor(UserContext.getLoginInfo());
        rechargeOffline.setAuditTime(new Date());
        rechargeOffline.setRemark(remark);
        rechargeOffline.setState(state);
        update(rechargeOffline);
        //审核通过state=1,
        if(state== BaseAuditDomain.STATE_SUCCESS){
            Account account = accountService.get(rechargeOffline.getApplier().getId());
            //修改余额
            account.setUsableAmount(account.getUsableAmount().add(rechargeOffline.getAmount()));
            accountService.update(account);
            //设置流水对象(交易成功之后,保存流水对象)
            //添加充值成功流水账
            accountFlowService.createRechargeAccountFlow(account,Constants.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,rechargeOffline.getAmount());
        }

        //审核失败state=2
    }

    private void update(RechargeOffline rechargeOffline) {
        int i = offlineMapper.updateByPrimaryKey(rechargeOffline);
        if (i == 0){
            throw new DisplayableException("乐观锁异常");
        }
    }
}
