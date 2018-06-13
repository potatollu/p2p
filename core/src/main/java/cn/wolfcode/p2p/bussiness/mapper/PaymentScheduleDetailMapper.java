package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.bussiness.domain.PaymentScheduleDetail;
import java.util.List;

public interface PaymentScheduleDetailMapper {

    int insert(PaymentScheduleDetail entity);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    List<PaymentScheduleDetail> selectAll();

    int updateByPrimaryKey(PaymentScheduleDetail entity);
}