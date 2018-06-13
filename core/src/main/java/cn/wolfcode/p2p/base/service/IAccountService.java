package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.Account;

public interface IAccountService {

    void init(Long id);

    Account selectById(Long id);

    Account get(Long id);

    void update(Account account);
}
