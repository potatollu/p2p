package cn.wolfcode.p2p.bussiness.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.bussiness.domain.SystemAccount;
import cn.wolfcode.p2p.bussiness.mapper.SystemAccountMapper;
import cn.wolfcode.p2p.bussiness.service.ISystemAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemAccountServiceImpl implements ISystemAccountService {
    @Autowired
    private SystemAccountMapper systemAccountMapper;

    public SystemAccount getCurrent() {
        return systemAccountMapper.getCurrent();
    }

    public void update(SystemAccount systemAccount) {
        int i = systemAccountMapper.updateByPrimaryKey(systemAccount);
        if (i == 0){
            throw new DisplayableException("SystemAccount乐观锁异常");
        }
    }
}
