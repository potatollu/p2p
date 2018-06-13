package cn.wolfcode.p2p.base.query;

public class BidRequestQueryObject extends QueryObject{

    private int bidRequestState = -1;//单个状态查询
    private int[] bidRequestStates;//多个状态查询

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }

    public int[] getBidRequestStates() {
        return bidRequestStates;
    }

    public void setBidRequestStates(int[] bidRequestStates) {
        this.bidRequestStates = bidRequestStates;
    }
}
