package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.bussiness.domain.BidRequest;

public interface IBidRequestAuditHistoryService {
    void save(int auditType, BidRequest br, String remark, int state);
}
