package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import cn.wolfcode.p2p.util.*;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SendVerifyCodeServiceImpl implements ISendVerifyCodeService{

    /**
     * 发送验证码到后台
     * @param phoneNumber
     */
    public void sendVerifyCode(String phoneNumber) {
        //判断手机号码是否合法
        AssertUtil.isEmptity(phoneNumber,"不正确的手机号码");
        //判断验证码发送的时间间隔
        Date currentTime = new Date();
        VerifyCodeVO lastVO = UserContext.getVerifyCodeVO();
        //不是第一次发送的话
        if (lastVO != null){
            Date sendTime = lastVO.getSendTime();
            boolean canSend = DateUtil.getSecondsBetween(currentTime,sendTime) > Constants.SEND_MESSAGE_INTERVAL;
            //生成验证码
            if (!canSend){
                throw new DisplayableException("验证码发送太频繁,请耐心等待");
            }
        }
        String code = UUID.randomUUID().toString().substring(0, 4);
        //执行发送
        System.out.println("你的验证码为"+code);
        //sendSms(phoneNumber,"你的验证码为"+code);
        smsSingleSender(phoneNumber,code);
        //创建验证码发送记录对象
        VerifyCodeVO vo = new VerifyCodeVO(code,phoneNumber ,currentTime);
        //讲对象发到session中
        UserContext.setVerifyCodeVO(vo);
    }

    /**
     * 发送验证码
     * @param phoneNumber
     * @param code
     */
    private void sendSms(String phoneNumber,String code){
        Map<String,String> map = new HashMap<String, String>();
        map.put("appkey","268493e9a71e501219bd21268e573e6b");
        map.put("mobile",phoneNumber);
        map.put("content","【成都创信信息】验证码为："+code+",欢迎注册平台！");
        try {
            String httpRequest = HttpUtil.sendHttpRequest("https://way.jd.com/chuangxin/dxjk", map);
            System.out.println(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void smsSingleSender(String phoneNumber,String code){
        SmsSingleSender sender = new SmsSingleSender(1400079298, "acf158bed7bb1f2a543e5b8d71b23bc0");
        ArrayList<String> params = new ArrayList<String>();
        params.add(code + "");
        params.add("30");
        try {
            SmsSingleSenderResult result = sender.sendWithParam("86", "18276660180",
                    102004, params, "", "", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DisplayableException("短信获取失败，请稍后尝试");
        }
    }
}
