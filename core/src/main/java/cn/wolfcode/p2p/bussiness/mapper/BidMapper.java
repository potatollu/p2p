package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.bussiness.domain.Bid;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BidMapper {

    int insert(Bid entity);

    Bid selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Bid entity);

    BigDecimal sumUserBidAmountByBidRequstId(@Param("bidRequestId") Long bidRequestId, @Param("userId") Long userId);

    void batchUpdateState(@Param("bidRequestId") Long bidRequestId, @Param("state") int state);

    /**
     * @return
     */
    List<Bid> getBids();

    int selecTtotalBids();
}