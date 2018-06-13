package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.util.PageResult;

public interface IRechargeOfflineService {
    void apply(RechargeOffline rechargeOffline);

    PageResult query(RechargeOfflineQueryObject qo);

    void audit(Long id, int state, String remark);
}
