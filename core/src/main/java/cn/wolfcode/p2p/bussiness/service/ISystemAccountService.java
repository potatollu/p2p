package cn.wolfcode.p2p.bussiness.service;

import cn.wolfcode.p2p.bussiness.domain.SystemAccount;

public interface ISystemAccountService {
    SystemAccount getCurrent();

    void update(SystemAccount systemAccount);
}
