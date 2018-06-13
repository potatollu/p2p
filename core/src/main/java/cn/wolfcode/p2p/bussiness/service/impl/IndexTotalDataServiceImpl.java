package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.bussiness.domain.IndexTotalData;
import cn.wolfcode.p2p.bussiness.mapper.BidMapper;
import cn.wolfcode.p2p.bussiness.mapper.BidRequestMapper;
import cn.wolfcode.p2p.bussiness.mapper.SystemAccountFlowMapper;
import cn.wolfcode.p2p.bussiness.service.IIndexTotalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class IndexTotalDataServiceImpl implements IIndexTotalDataService {
    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private BidRequestMapper bidRequestMapper;
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    /**
     * 从数据库查询总投资人数、总借款人数、总借款金额、已经赚取总利息、总待收本息
     * 封装进对象中
     * @return
     */
    public IndexTotalData account() {
        IndexTotalData indexTotalData = new IndexTotalData();
        int totalBids = bidMapper.selecTtotalBids();
        int totalBidRequest = bidRequestMapper.selectTotalBidRequest();
        BigDecimal totalRequestAmount = bidRequestMapper.selectTotalRequestAmount();
        BigDecimal totalRewardInterest = systemAccountFlowMapper.selectTotalRewardInterest();
        indexTotalData.setTotalBids(totalBids);
        indexTotalData.setTotalBidRequest(totalBidRequest);
        indexTotalData.setTotalRequestAmount(totalRequestAmount);
        indexTotalData.setTotalRewardInterest(totalRewardInterest);
        return indexTotalData;
    }
}
