package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoAuthMapper {

    int insert(VideoAuth entity);

    VideoAuth selectByPrimaryKey(Long id);

    int updateByPrimaryKey(VideoAuth entity);

    /**
     * 根据状态获取用户是否已经有预约信息
     * @param
     * @param
     * @return
     */
    VideoAuth getByStateAndUserId(@Param("state") int state,
                                  @Param("userId") Long userId);

    List<VideoAuth> queryPage(VideoAuthQueryObject qo);
}