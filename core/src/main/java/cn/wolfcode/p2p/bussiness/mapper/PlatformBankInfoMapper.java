package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.bussiness.domain.PlatformBankInfo;
import java.util.List;

public interface PlatformBankInfoMapper {

    int insert(PlatformBankInfo entity);

    PlatformBankInfo selectByPrimaryKey(Long id);

    List<PlatformBankInfo> selectAll();

    int updateByPrimaryKey(PlatformBankInfo entity);

    int queryForCount(QueryObject qo);

    List<PlatformBankInfo> queryForList(QueryObject qo);
}