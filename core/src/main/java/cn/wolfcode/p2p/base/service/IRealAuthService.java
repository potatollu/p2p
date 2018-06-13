package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface IRealAuthService {

    void apply(RealAuth realAuth);

    RealAuth get(Long realAuthId);

    PageInfo query(RealAuthQueryObject qo);

    void audict(Long id, int state, String remark);
}
