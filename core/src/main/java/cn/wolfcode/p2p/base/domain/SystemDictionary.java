package cn.wolfcode.p2p.base.domain;
import cn.wolfcode.p2p.base.domain.BaseDomain;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典
 */
public class SystemDictionary extends BaseDomain {
    private String sn;
    private String title;


    //在对象中返回一个json格式的数据
    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("sn",sn);
        map.put("title",title);
        return JSON.toJSONString(map);
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
