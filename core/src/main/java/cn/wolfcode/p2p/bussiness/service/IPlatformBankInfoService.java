package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.bussiness.domain.PlatformBankInfo;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.util.PageResult;

import java.util.List;

public interface IPlatformBankInfoService {
    List<PlatformBankInfo> selectAll();

    void saveOrUpdate(RechargeOffline rechargeOffline);

    PageResult query(QueryObject qo);

    void saveOrUpdaet(PlatformBankInfo bankInfo);
}
