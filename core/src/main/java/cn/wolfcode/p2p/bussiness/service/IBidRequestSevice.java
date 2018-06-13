package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.util.PageResult;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Date;

public interface IBidRequestSevice {
    void apply(BidRequest br);

    PageInfo queryPage(BidRequestQueryObject qo);

    void publishAudit(Long id, int state, Date publishTime, String remark);

    void update(BidRequest bidRequest);

    PageResult queryForList(BidRequestQueryObject qo);

    void bidRequestPublishCheck();

    BidRequest get(Long id);

    void bid(Long bidRequestId, BigDecimal amount);

    void audit(Long id, int state, String remark);

    void audit2(Long id, int state, String remark);

    void returnMone(Long id);
}
