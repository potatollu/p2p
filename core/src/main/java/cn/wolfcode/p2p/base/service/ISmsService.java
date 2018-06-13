package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.bussiness.domain.SendSms;

import java.util.List;

public interface ISmsService {

    int insert(SendSms entity);

    List<SendSms> selectByUserId(Long userId);

    void updateState();

    int countMesage();
}
