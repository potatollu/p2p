package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.bussiness.domain.Bid;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.mapper.BidMapper;
import cn.wolfcode.p2p.bussiness.service.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BidServiceImpl implements IBidService {
    @Autowired
    private BidMapper bidMapper;

    /**
     * 查询用户针对某一个借款的投标总额
     * @param
     * @return
     */
    public BigDecimal sumUserBidAmountByBidRequstId(Long bidRequestId, Long userId) {
        return bidMapper.sumUserBidAmountByBidRequstId(bidRequestId,userId);
    }

    public void save(BidRequest bidRequest, BigDecimal amount, LoginInfo currentUser) {
        Bid bid = new Bid();
        bid.setActualRate(bidRequest.getCurrentRate());
        bid.setAvailableAmount(amount);
        bid.setBidRequestId(bidRequest.getId());
        bid.setBidRequestState(bidRequest.getBidRequestState());
        bid.setBidRequestTitle(bidRequest.getTitle());
        bid.setBidTime(new Date());
        bid.setBidUser(currentUser);
        bidMapper.insert(bid);
    }

    public void batchUpdateState(Long bidRequestId, int state) {
        bidMapper.batchUpdateState(bidRequestId,state);
    }

    @Override
    public List<Bid> getBids() {
        return bidMapper.getBids();
    }
}
