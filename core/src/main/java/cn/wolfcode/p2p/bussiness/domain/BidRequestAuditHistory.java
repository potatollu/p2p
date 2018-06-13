package cn.wolfcode.p2p.bussiness.domain;

import cn.wolfcode.p2p.base.domain.BaseAuditDomain;

/**
 * 审核相关信息
 */
public class BidRequestAuditHistory extends BaseAuditDomain{

    //审核类型
    public static final int TYPE_PUBLISH = 0;//发标审核
    public static final int TYPE_AUDIT1 = 1;//满标一审
    public static final int TYPE_AUDIT2 = 2;//满标二审

    private Long bidRequestId;//贷款人
    private int auditType;//审核类型

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public int getAuditType() {
        return auditType;
    }

    public void setAuditType(int auditType) {
        this.auditType = auditType;
    }


}
