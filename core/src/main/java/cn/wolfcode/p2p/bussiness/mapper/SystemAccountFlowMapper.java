package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.bussiness.domain.SystemAccountFlow;

import java.math.BigDecimal;
import java.util.List;

public interface SystemAccountFlowMapper {

    int insert(SystemAccountFlow entity);

    int updateByPrimaryKey(SystemAccountFlow entity);

    BigDecimal selectTotalRewardInterest();
}