package cn.wolfcode.p2p.bussiness.mapper;

import cn.wolfcode.p2p.bussiness.domain.SendSms;
import java.util.List;

public interface SendSmsMapper {

    int insert(SendSms entity);

    List<SendSms> selectByUserId(Long userId);

    void updateState();

    int countMesage();
}