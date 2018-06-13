package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RealAuthMapper {

    int insert(RealAuth entity);

    RealAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(RealAuth entity);

    List<RealAuth> queryPage(RealAuthQueryObject qo);
}