package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 初始化Account对象
     * @param id
     */
    public void init(Long id) {
        Account account = new Account();
        account.setId(id);
        accountMapper.insert(account);
    }

    public Account selectById(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public void update(Account account){
        int i = accountMapper.updateByPrimaryKey(account);
        if (i == 0 ){
            throw new DisplayableException("Account乐观锁异常");
        }
    }
}
