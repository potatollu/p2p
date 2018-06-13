package cn.wolfcode.p2p.base.event;


import cn.wolfcode.p2p.base.domain.LoginInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 借款成功、失败发送消息
 */
public class BidSuccessEvent extends ApplicationEvent {

    //事件关联的对象
    private LoginInfo loginInfo;

    /**
     * 事件源
     * @param source
     */
    public BidSuccessEvent(Object source, LoginInfo loginInfo) {
        super(source);
        this.loginInfo = loginInfo;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
