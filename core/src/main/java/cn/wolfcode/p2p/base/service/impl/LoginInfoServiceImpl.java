package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import cn.wolfcode.p2p.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.applet.AppletListener;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoginInfoServiceImpl implements ILoginInfoService,ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IIpLogService ipLogService;

    public LoginInfo selectById(Long id) {
        return loginInfoMapper.selectById(id);
    }

    /**
     * 注册验证
     * @param username 账户名
     * @param verifycode 验证码
     * @param password 密码
     * @param confirmPwd 确认密码
     */
    public void regist(String username, String verifycode, String password, String confirmPwd) {
        //判断传进来的参数是否为空
        AssertUtil.isEmptity(username,"账号不能为空");
        AssertUtil.isEmptity(verifycode,"验证码不能为空");
        AssertUtil.isEmptity(password,"密码不能为空");
        AssertUtil.isEquals(password,confirmPwd,"密码不相同");

        //注册时验证码验证
        VerifyCodeVO vo = UserContext.getVerifyCodeVO();
        if (vo == null){
            throw new DisplayableException("请输入验证码");
        }
        if (!vo.getCode().equals(verifycode)){
            throw new DisplayableException("验证码错误");
        }
        //接受验证码手机要和注册的手机一直
        if (!vo.getPhoneNumber().equals(username)){
            throw new DisplayableException("手机号码不一致");
        }
        //验证码有效时间
        boolean canRegist = DateUtil.getSecondsBetween(vo.getSendTime(),new Date()) < Constants.MESSAGE_VAILED_TIME;
        if (!canRegist){
            throw new DisplayableException("验证码失效,请重新发送");
        }

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        loginInfo.setPassword(MD5.encode(password+username));
        loginInfo.setState(LoginInfo.STATE_NOMAL);
        loginInfo.setUserType(LoginInfo.LOGIN_USER);
        loginInfoMapper.insert(loginInfo);

        //初始化account
        accountService.init(loginInfo.getId());
        //初始化userInfo
        userInfoService.init(loginInfo.getId(),loginInfo.getUsername());

    }

    /**
     * 判断是否已注册
     * @param username
     * @return
     */
    public boolean existUsername(String username) {
        return loginInfoMapper.existUsername(username)>0;
    }

    /**
     * 登录
     * @param username
     * @param password
     */
    public void login(String username, String password,boolean isMgrsite) {
        AssertUtil.isEmptity(username,"用户名不能为空");
        AssertUtil.isEmptity(password,"密码不能为空");
        LoginInfo loginInfo = loginInfoMapper.login(username,MD5.encode(password+username));
        AssertUtil.isObjectEmptity(loginInfo,"账号或密码不正确");

        //isMgrsite:true 是后台登录
        if(isMgrsite && !loginInfo.isMgrSiteUser()){
            throw new DisplayableException("用户类型错误");
        }
        if(!isMgrsite && loginInfo.isMgrSiteUser()){
            throw new DisplayableException("用户类型错误");
        }

        UserContext.setLoginInfo(loginInfo);
    }

    /**
     * 根据用户类型查列表
     * @param
     * @return
     */
    public List<LoginInfo> listByUserType(int userType) {
        return loginInfoMapper.listByUserType(userType);
    }

    public void initAdmin(){
        if (existUsername("admin"))return;
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername("admin");
        loginInfo.setPassword(MD5.encode("00000admin"));
        loginInfo.setUserType(LoginInfo.LOGIN_MENAGER);
        loginInfoMapper.insert(loginInfo);
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        initAdmin();
    }
}
