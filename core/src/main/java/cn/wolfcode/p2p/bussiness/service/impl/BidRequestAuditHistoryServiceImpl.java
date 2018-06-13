package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.bussiness.mapper.BidRequestAuditHistoryMapper;
import cn.wolfcode.p2p.bussiness.service.IBidRequestAuditHistoryService;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
    @Autowired
    private BidRequestAuditHistoryMapper historyMapper;

    public void save(int auditType, BidRequest br,String remark,int state) {
        BidRequestAuditHistory auditHistory = new BidRequestAuditHistory();
        auditHistory.setAuditType(auditType);
        auditHistory.setBidRequestId(br.getId());
        auditHistory.setApplier(br.getCreateUser());
        auditHistory.setApplyTime(br.getApplyTime());
        auditHistory.setAuditor(UserContext.getLoginInfo());
        auditHistory.setAuditTime(new Date());
        auditHistory.setRemark(remark);
        auditHistory.setState(state);
        historyMapper.insert(auditHistory);
    }
}
