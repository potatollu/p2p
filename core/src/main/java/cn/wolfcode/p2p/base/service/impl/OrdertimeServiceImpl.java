package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.mapper.OrderTimeMapper;
import cn.wolfcode.p2p.base.service.IOrdertimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class OrdertimeServiceImpl implements IOrdertimeService {

    @Autowired
    private OrderTimeMapper orderTimeMapper;

    public List<OrderTime> selectAll() {
        return orderTimeMapper.selectAll();
    }

    @Override
    public OrderTime get(Long timeId) {
        return orderTimeMapper.selectByPrimaryKey(timeId);
    }
}
