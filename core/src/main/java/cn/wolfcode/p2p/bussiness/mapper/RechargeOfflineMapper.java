package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.base.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import java.util.List;

public interface RechargeOfflineMapper {

    int insert(RechargeOffline entity);

    RechargeOffline selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeOffline entity);

    int queryForCount(RechargeOfflineQueryObject qo);

    List<RechargeOffline> queryForList(RechargeOfflineQueryObject qo);
}