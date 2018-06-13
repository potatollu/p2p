package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.bussiness.domain.Bid;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;

import java.math.BigDecimal;
import java.util.List;

public interface IBidService {
    BigDecimal sumUserBidAmountByBidRequstId(Long bidRequestId, Long userId);

    void save(BidRequest bidRequest, BigDecimal amount, LoginInfo currentUser);

    /**
     * 根据借款对象id,修改投标对象的状态
     * @param bidRequestId
     * @param state
     */
    void batchUpdateState(Long bidRequestId, int state);

    List<Bid> getBids();
}
