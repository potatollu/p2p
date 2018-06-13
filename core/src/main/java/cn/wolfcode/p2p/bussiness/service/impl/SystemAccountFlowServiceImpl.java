package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.bussiness.domain.SystemAccount;
import cn.wolfcode.p2p.bussiness.domain.SystemAccountFlow;
import cn.wolfcode.p2p.bussiness.mapper.SystemAccountFlowMapper;
import cn.wolfcode.p2p.bussiness.service.ISystemAccountFlowService;
import cn.wolfcode.p2p.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    public void systemManagementChargeFlow(SystemAccount account, BigDecimal amount){
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setActionTime(new Date());
        flow.setActionType(Constants.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setAmount(amount);
        flow.setFreezedAmount(account.getFreezedAmount());
        flow.setNote("交付平台管理费:"+account+"元");
        flow.setUsableAmount(account.getUsableAmount());
        systemAccountFlowMapper.insert(flow);
    }

    @Override
    public void createInterestManegerFlow(SystemAccount systemAccount, BigDecimal interest) {
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setActionTime(new Date());
        flow.setActionType(Constants.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE);
        flow.setAmount(interest);
        flow.setFreezedAmount(systemAccount.getFreezedAmount());
        flow.setNote("收取利息管理费:"+systemAccount+"元");
        flow.setUsableAmount(systemAccount.getUsableAmount());
        systemAccountFlowMapper.insert(flow);
    }

}
