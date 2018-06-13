package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.UserInfo;

public interface IUserInfoService {
    void init(Long id, String phoneNumber);

    UserInfo get(Long id);

    void basicInfoSave(UserInfo userInfo);

    void update(UserInfo userInfo);
}
