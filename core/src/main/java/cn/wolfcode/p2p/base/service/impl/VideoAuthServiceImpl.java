package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.mapper.VideoAuthMapper;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IOrdertimeService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.util.BitStatesUtils;
import cn.wolfcode.p2p.util.DateUtil;
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
public class VideoAuthServiceImpl implements IVideoAuthService {

    @Autowired
    private VideoAuthMapper videoAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IOrdertimeService ordertimeService;

    public void apply(Long auditorId, String orderDate, Long timeId) {
        //参数基本判断
        //判断用户是否已经有预约时间
        LoginInfo loginInfo = UserContext.getLoginInfo();
        VideoAuth tempVideoAuth = videoAuthMapper.getByStateAndUserId(VideoAuth.STATE_NOMAL, loginInfo.getId());
        if (tempVideoAuth != null){
            throw new DisplayableException("该用户已经有预约时间");
        }
        //判断用户是否已经完成视频认证
        UserInfo userInfo = userInfoService.get(loginInfo.getId());
        if (userInfo.hasVedioAuth()){
            throw new DisplayableException("该用户已完成视频认证");
        }

        //时间段
        OrderTime orderTime = ordertimeService.get(timeId);

        //保存视频认证对象
        VideoAuth videoAuth = new VideoAuth();
        videoAuth.setApplier(loginInfo);
        videoAuth.setApplyTime(new Date());
        videoAuth.setState(VideoAuth.STATE_NOMAL);

        //拼接预约时间
        String beginDateStr = orderDate +" "+ orderTime.getBegin() + ":00";
        String endDateStr = orderDate +" "+ orderTime.getEnd() + ":00";
        videoAuth.setOrderBeginDate(DateUtil.parseDate(beginDateStr));
        videoAuth.setOrderEndDate(DateUtil.parseDate(endDateStr));
        LoginInfo tempLoginInfo = new LoginInfo();
        tempLoginInfo.setId(auditorId);
        videoAuth.setAuditor(tempLoginInfo);
        videoAuthMapper.insert(videoAuth);
    }


    public VideoAuth getByStateAndUserId(int state, Long userId) {
        return videoAuthMapper.getByStateAndUserId(state,userId);
    }

    /**
     * 分页
     * @param qo
     * @return
     */
    public PageInfo query(VideoAuthQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        List<VideoAuth> list = videoAuthMapper.queryPage(qo);
        return new PageInfo(list);
    }

    /**
     * 视频审核
     * @param id
     * @param state
     * @param remark
     */
    public void audit(Long id, int state, String remark) {
        //视频认证状态是否待审核
        VideoAuth videoAuth = videoAuthMapper.selectByPrimaryKey(id);
        if (videoAuth.getState() != VideoAuth.STATE_NOMAL){
            throw new DisplayableException("该对象不处于待审核状态");
        }
        //sheHi审核相关
        videoAuth.setAuditTime(new Date());
        videoAuth.setState(state);
        videoAuth.setRemark(remark);
        update(videoAuth);
        //如果审核通过:
        //修改userinfo.bitbitState,添加视频审核通过状态
        if(state == VideoAuth.STATE_SUCCESS){
            UserInfo userInfo = userInfoService.get(videoAuth.getApplier().getId());
            if (userInfo.hasVedioAuth()){
                throw new DisplayableException("用户已完成认证");
            }
            Long tempState = BitStatesUtils.addState(userInfo.getBitState(), BitStatesUtils.OP_VEDIO_AUTH);
            userInfo.setBitState(tempState);
            userInfoService.update(userInfo);
        }
    }

    private void update(VideoAuth videoAuth) {
        int i = videoAuthMapper.updateByPrimaryKey(videoAuth);
        if (i == 0){
            throw new DisplayableException("审核信息修改失败");
        }
    }
}
