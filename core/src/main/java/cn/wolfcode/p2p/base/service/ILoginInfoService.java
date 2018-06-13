package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.List;

public interface ILoginInfoService {
    LoginInfo selectById(Long id);

    void regist(String username, String verifycode, String password, String confirmPwd);

    boolean existUsername(String username);

    void login(String username, String password,boolean isMgrsite);

    List<LoginInfo> listByUserType(int userType);
}
