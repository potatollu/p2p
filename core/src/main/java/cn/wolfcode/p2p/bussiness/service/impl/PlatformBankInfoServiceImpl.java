package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.bussiness.domain.PlatformBankInfo;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.bussiness.mapper.PlatformBankInfoMapper;
import cn.wolfcode.p2p.bussiness.mapper.RechargeOfflineMapper;
import cn.wolfcode.p2p.bussiness.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    /**
     * 查询所有
     * @return
     */
    public List<PlatformBankInfo> selectAll() {
        return platformBankInfoMapper.selectAll();
    }

    /**
     * 保存跟新
     * @param rechargeOffline
     */
    public void saveOrUpdate(RechargeOffline rechargeOffline) {
        if (rechargeOffline.getId() != null){
            rechargeOfflineMapper.updateByPrimaryKey(rechargeOffline);
        }else {
            rechargeOfflineMapper.insert(rechargeOffline);
        }
    }

    /**
     * 分页
     * @param qo
     * @return
     */
    public PageResult query(QueryObject qo) {
        int i = platformBankInfoMapper.queryForCount(qo);
        if (i==0){
            PageResult.empty(qo.getPageSize());
        }
        List<PlatformBankInfo> list = platformBankInfoMapper.queryForList(qo);
        return new PageResult(list,i,qo.getCurrentPage(),qo.getPageSize());
    }

    /**
     * 保存/更新
     * @param bankInfo
     */
    public void saveOrUpdaet(PlatformBankInfo bankInfo) {
        if (bankInfo.getId() != null){
            platformBankInfoMapper.updateByPrimaryKey(bankInfo);
        }else {
            platformBankInfoMapper.insert(bankInfo);
        }
    }
}
