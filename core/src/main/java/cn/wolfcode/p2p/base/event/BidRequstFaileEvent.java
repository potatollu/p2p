package cn.wolfcode.p2p.base.event;


import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import org.springframework.context.ApplicationEvent;

/**
 * 借款成功、失败发送消息
 */
public class BidRequstFaileEvent extends ApplicationEvent {

    //事件关联的对象
    private BidRequest bidRequest;

    /**
     * 事件源
     * @param source
     */
    public BidRequstFaileEvent(Object source, BidRequest bidRequest) {
        super(source);
        this.bidRequest = bidRequest;
    }

    public BidRequest getBidRequest() {
        return bidRequest;
    }

    public void setBidRequest(BidRequest bidRequest) {
        this.bidRequest = bidRequest;
    }
}
