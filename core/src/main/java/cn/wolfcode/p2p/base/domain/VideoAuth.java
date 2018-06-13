package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 视频审核对象
 */
public class VideoAuth extends BaseAuditDomain{
    //预约时间段
    //开始预约时间
    private Date orderBeginDate;
    //预约结束时间
    private Date orderEndDate;

    public String getJsonString(){
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("id",id);
        map.put("username",applier.getUsername());
        map.put("orderTime", DateFormat.getDateInstance().format(orderBeginDate)
                +" "+DateFormat.getDateInstance().format(orderEndDate));
        return JSON.toJSONString(map);
    }

    public Date getOrderBeginDate() {
        return orderBeginDate;
    }

    public void setOrderBeginDate(Date orderBeginDate) {
        this.orderBeginDate = orderBeginDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }
}
