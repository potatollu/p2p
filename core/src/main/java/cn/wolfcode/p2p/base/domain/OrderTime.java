package cn.wolfcode.p2p.base.domain;

/**
 * 视频预约时间段
 */
public class OrderTime extends BaseDomain{
    //开始时间
    private String begin;
    //结束时间
    private String end;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin == null ? null : begin.trim();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end == null ? null : end.trim();
    }
}