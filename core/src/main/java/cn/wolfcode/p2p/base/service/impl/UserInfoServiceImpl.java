package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.UserInfoMapper;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.AssertUtil;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 初始化UserInfo对象
     * @param id
     * @param phoneNumber
     */
    public void init(Long id, String phoneNumber) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPhoneNumber(phoneNumber);
        userInfoMapper.insert(userInfo);
    }

    public UserInfo get(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存基本信息
     * @param userInfo
     */
    public void basicInfoSave(UserInfo userInfo) {
        //基本判断
        //AssertUtil.isObjectEmptity(userInfo,"数据错误");
        //保存基本资料
        //如果是第一次保存,修改用户状态,添加已完成
        Long userId = UserContext.getLoginInfo().getId();
        userInfo.setId(userId);
        UserInfo olderInfo = userInfoMapper.selectByPrimaryKey(userId);
        //解决延迟加载的问题,先出发get方法
        olderInfo.getEducationBackground();
        olderInfo.getHouseCondition();
        olderInfo.getIncomeGrade();
        olderInfo.getKidCount();
        olderInfo.getMarriage();
        //不可以直接调用插入方法
        olderInfo.setEducationBackground(userInfo.getEducationBackground());
        olderInfo.setHouseCondition(userInfo.getHouseCondition());
        olderInfo.setIncomeGrade(userInfo.getIncomeGrade());
        olderInfo.setMarriage(userInfo.getMarriage());
        olderInfo.setKidCount(userInfo.getKidCount());

        //判断是否已完成填写基本资料
        if(!olderInfo.isBasicInfo()){
            //将原来未填写状态变成已填写状态
            Long state = BitStatesUtils.addState(olderInfo.getBitState(),BitStatesUtils.OP_BASIC_INFO);
            olderInfo.setBitState(state);
        }
        update(olderInfo);

    }

    public void update(UserInfo userInfo){
        int count = userInfoMapper.updateByPrimaryKey(userInfo);
        if (count == 0){
            throw new DisplayableException("用户基本信息修改失败,请联系客服,[乐观锁ID:]");
        }
    }
}
