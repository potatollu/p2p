package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import com.github.pagehelper.PageInfo;

public interface IIpLogService {

    int insert(IpLog entity);

    PageInfo queryPage(IpLogQueryObject qo);

    void save(int loginUser, String username, String ip, int state,int userType );
}
