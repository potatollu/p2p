package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.base.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.bussiness.domain.PaymentSchedule;
import java.util.List;

public interface PaymentScheduleMapper {

    int insert(PaymentSchedule entity);

    PaymentSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentSchedule entity);

    int queryForCount(PaymentScheduleQueryObject qo);

    List<PaymentSchedule> queryForList(PaymentScheduleQueryObject qo);

    List<PaymentSchedule> listByBidRequestid(Long bidRequestId);
}