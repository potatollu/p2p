package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

public interface LoginInfoMapper {
    LoginInfo selectById(Long id);

    void insert(LoginInfo loginInfo);

    int existUsername(String username);

    LoginInfo login(@Param("username") String username,
                    @Param("password") String password);

    List<LoginInfo> listByUserType(int userType);
}
