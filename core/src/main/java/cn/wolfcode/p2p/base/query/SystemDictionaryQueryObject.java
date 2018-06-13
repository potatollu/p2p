package cn.wolfcode.p2p.base.query;

public class SystemDictionaryQueryObject extends QueryObject{
    private String keyword;
    private Long parentId = -1L;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
