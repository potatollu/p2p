package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface IVideoAuthService {
    void apply(Long auditorId, String orderDate, Long timeId);

    VideoAuth getByStateAndUserId(int state, Long userId);

    PageInfo query(VideoAuthQueryObject qo);

    void audit(Long id, int state, String remark);
}
