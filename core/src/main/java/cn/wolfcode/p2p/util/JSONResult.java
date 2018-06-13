package cn.wolfcode.p2p.util;

public class JSONResult {
    public JSONResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public JSONResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    private boolean success = true;
    private String msg;

    public JSONResult remark(String msg){
        this.success = false;
        this.msg = msg;
        return this;
    }
}
