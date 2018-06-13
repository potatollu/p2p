package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.event.BidRequstFaileEvent;
import cn.wolfcode.p2p.base.event.BidRequstSuccessEvent;
import cn.wolfcode.p2p.base.event.BidSuccessEvent;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISmsService;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.domain.SendSms;
import cn.wolfcode.p2p.bussiness.mapper.SendSmsMapper;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 监听事件
 */
@Service
@Transactional
public class SmsServiceImpl implements ISmsService,ApplicationListener<ApplicationEvent> {

    @Autowired
    private SendSmsMapper sendSmsMapper;

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof BidRequstFaileEvent){
            bidRequstFaile(((BidRequstFaileEvent) event).getBidRequest());
        }else if(event instanceof BidRequstSuccessEvent){
            bidRequstSuccess(((BidRequstSuccessEvent) event).getBidRequest());
        }else if (event instanceof BidSuccessEvent){
            bidSuccess(((BidSuccessEvent) event).getLoginInfo());
        }
    }
    /**
     * 投标成功：给投资人发送一个 站内消息/短信
     */
    private void bidSuccess(LoginInfo loginInfo){

        String bidSuccess = "用户："+loginInfo.getUsername()+"投标成功";
        String msg = "投标成功";
        SendSms sendSms = new SendSms();
        sendSms.setBidSuccess(bidSuccess);
        sendSms.setUserId(loginInfo.getId());
        sendSmsMapper.insert(sendSms);
        //发送短信
        smsSingleSender(loginInfo.getUsername(),msg);
    }

    /**
     *借款成功:发送一个借款成功 站内消息/短信
     */
    private void bidRequstSuccess(BidRequest bidRequest){
        String bidRequstSuccess = "用户："+bidRequest.getCreateUser().getUsername()+"借款成功";
        String msg = "借款成功";
        SendSms sendSms = new SendSms();
        sendSms.setBidRequstSuccess(bidRequstSuccess);
        sendSms.setUserId(bidRequest.getCreateUser().getId());
        sendSmsMapper.insert(sendSms);
        //发送短信
        smsSingleSender(bidRequest.getCreateUser().getUsername(),msg);
    }

    /**
     * 发送借款失败 站内消息/短信
     */
    private void bidRequstFaile(BidRequest bidRequest){
        String bidRequstFaile = "用户："+bidRequest.getCreateUser().getUsername()+"借款失败";
        String msg = "借款失败";
        SendSms sendSms = new SendSms();
        sendSms.setUserId(bidRequest.getCreateUser().getId());
        sendSms.setBidRequstFaile(bidRequstFaile);
        sendSmsMapper.insert(sendSms);
        //发送短信
        smsSingleSender(bidRequest.getCreateUser().getUsername(),msg);
    }

    private void smsSingleSender(String phoneNumber,String msg){
        SmsSingleSender sender = new SmsSingleSender(1400079298, "acf158bed7bb1f2a543e5b8d71b23bc0");
        ArrayList<String> params = new ArrayList<String>();
        params.add(phoneNumber);
        params.add(msg);
        try {
            SmsSingleSenderResult result = sender.sendWithParam("86", "18276660180",
                    102004, params, "", "", "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DisplayableException("短信获取失败，请稍后尝试");
        }
    }


    @Override
    public int insert(SendSms entity) {
        return sendSmsMapper.insert(entity);
    }


    @Override
    public List<SendSms> selectByUserId(Long userId) {
        return sendSmsMapper.selectByUserId(userId);
    }

    @Override
    public void updateState() {
        sendSmsMapper.updateState();
    }

    @Override
    public int countMesage() {
        int count = sendSmsMapper.countMesage();
        return count;
    }

}
