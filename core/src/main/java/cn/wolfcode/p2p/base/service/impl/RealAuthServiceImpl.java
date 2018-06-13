package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.RealAuthMapper;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.AssertUtil;
import cn.wolfcode.p2p.util.BitStatesUtils;
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
public class RealAuthServiceImpl implements IRealAuthService {

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RealAuthMapper realAuthMapper;

    /**
     * 实名认证申请
     *
     * @param realAuth
     */
    public void apply(RealAuth realAuth) {
        //参数判断
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.get(loginInfo.getId());
        AssertUtil.isTrue(userInfo.getRealAuthId() != null, "对不起,该用户正在审核");

        //保存实名申请
        RealAuth newRealAuth = new RealAuth();
        newRealAuth.setState(realAuth.getState());
        newRealAuth.setRealName(realAuth.getRealName());
        newRealAuth.setSex(realAuth.getSex());
        newRealAuth.setIdNumber(realAuth.getIdNumber());
        newRealAuth.setBornDate(realAuth.getBornDate());
        newRealAuth.setAddress(realAuth.getAddress());
        newRealAuth.setImage1(realAuth.getImage1());
        newRealAuth.setImage2(realAuth.getImage2());
        newRealAuth.setApplier(loginInfo);
        newRealAuth.setApplyTime(new Date());

        realAuthMapper.insert(newRealAuth);

        //把实名申请对象的id设置到userinfo.realAuthId中
        userInfo.setRealAuthId(newRealAuth.getId());
        userInfoService.update(userInfo);
    }

    public RealAuth get(Long realAuthId) {
        return realAuthMapper.selectByPrimaryKey(realAuthId);
    }

    /**
     * 分页查询
     *
     * @param qo
     * @return
     */
    public PageInfo query(RealAuthQueryObject qo) {
        //静态方法,底层绑定的ThreadLocal,
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<RealAuth> list = realAuthMapper.queryPage(qo);
        return new PageInfo(list);
    }

    /**
     * 后台审核认证
     *
     * @param id
     * @param state
     * @param mark
     */
    public void audict(Long id, int state, String remark) {
        //判断认证对象是否是处于等待审核
        RealAuth realAuth = realAuthMapper.selectByPrimaryKey(id);
        if (realAuth.getState() != RealAuth.STATE_NOMAL) {
            throw new DisplayableException("该用户不是等待审核状态");
        }
        realAuth.setAuditor(UserContext.getLoginInfo());
        realAuth.setState(state);
        realAuth.setRemark(remark);
        realAuth.setAuditTime(new Date());
        update(realAuth);
        //审核通过-->修改实名认证对象状态为审核通过->修改userInfo.bitState加上实名认证状态
        UserInfo userInfo = userInfoService.get(realAuth.getApplier().getId());
        if (state == RealAuth.STATE_SUCCESS) {
            if (userInfo.isRealAuth()) {
                throw new DisplayableException("用户已完成实名认证");
            }
            Long bitState = BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_REAL_AUTH);
            userInfo.setBitState(bitState);
        } else {
            //审核拒绝-->修改实名认证对象状态为审核拒绝->修改userInfo.realAuthId为null
            userInfo.setRealAuthId(null);
        }
        userInfoService.update(userInfo);
    }

    private void update(RealAuth realAuth) {
        int i = realAuthMapper.updateByPrimaryKey(realAuth);
        if (i == 0) {
            throw new DisplayableException("乐观锁异常:" + realAuth.getId());
        }
    }
}






