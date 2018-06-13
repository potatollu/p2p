package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.mapper.IpLogMapper;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IpLogServiceImpl implements IIpLogService {

    @Autowired
    private IpLogMapper ipLogMapper;

    public int insert(IpLog entity) {

        return ipLogMapper.insert(entity);
    }

    /**
     * 分页
     * @param qo
     * @return
     */
    public PageInfo queryPage(IpLogQueryObject qo) {
        //静态方法,底层绑定的ThreadLocal,
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<IpLog> list = ipLogMapper.queryPage(qo);
        return new PageInfo(list);
    }

    /**
     * 保存日志
     * @param loginUser
     * @param username
     * @param ip
     * @param userType
     * @param state
     */
    public void save(int loginUser, String username, String ip,int state ,int userType ) {
        IpLog ipLog = new IpLog();
        ipLog.setUsername(username);
        ipLog.setLoginTime(new Date());
        ipLog.setIp(UserContext.getIp());
        ipLog.setState(state);
        ipLog.setUserType(userType);
        ipLogMapper.insert(ipLog);
    }
}
