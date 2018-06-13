package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.base.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.domain.PaymentSchedule;
import cn.wolfcode.p2p.util.PageResult;

import java.util.List;

public interface IPaymentScheduleService {
    List<PaymentSchedule> createPaymentSchedule(BidRequest br);

    PageResult query(PaymentScheduleQueryObject qo);

    PaymentSchedule get(Long id);

    List<PaymentSchedule> listByBidRequestid(Long bidRequestId);

    void update(PaymentSchedule ps);
}
