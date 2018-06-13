package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.bussiness.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.bussiness.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.bussiness.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Dispatch;

@Service
@Transactional
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
    @Autowired
    private PaymentScheduleDetailMapper detailMapper;

    public void update(PaymentScheduleDetail detail) {
        int i = detailMapper.updateByPrimaryKey(detail);
        if (i == 0 ){
            throw new DisplayableException("收款失败");
        }
    }
}
