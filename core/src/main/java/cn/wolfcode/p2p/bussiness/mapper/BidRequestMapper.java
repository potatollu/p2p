package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;

import java.math.BigDecimal;
import java.util.List;

public interface BidRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidRequest entity);

    BidRequest selectByPrimaryKey(Long id);

    List<BidRequest> selectAll();

    int updateByPrimaryKey(BidRequest entity);

    List<BidRequest> queryPage(BidRequestQueryObject qo);

    List<BidRequest> queryForList(BidRequestQueryObject qo);

    int queryForCount(BidRequestQueryObject qo);

    int selectTotalBidRequest();

    BigDecimal selectTotalRequestAmount();
}