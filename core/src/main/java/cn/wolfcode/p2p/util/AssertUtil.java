package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import org.springframework.util.StringUtils;

public class AssertUtil {
    public static void isEmptity(String str,String msg) {
        if (!StringUtils.hasLength(str)) {
            throw new DisplayableException(msg);
        }
    }

    public static void isObjectEmptity(Object obj,String msg) {
        if (obj==null){
            throw new DisplayableException(msg);
        }
    }

    public static void isEquals(String str1,String str2,String msg) {
        if (!str1.equals(str2)) {
            throw new DisplayableException(msg);
        }
    }

    public static void isTrue(boolean s,String msg) {
        if (s) {
            throw new DisplayableException(msg);
        }
    }
}
