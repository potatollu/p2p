package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.util.BitStatesUtils;

/**
 * 用户基本信息
 */
public class UserInfo extends BaseDomain{
    //版本号
    private int version;
    //用户状态值
    private Long bitState = 0L;
    //用户实值
    private String realName;
    //用户身份证
    private String idNumber;
    //用户手机号码
    private String phoneNumber;
    //邮箱
    private String email;
    //收入
    private SystemDictionaryItem incomeGrade;
    //婚姻状况
    private SystemDictionaryItem marriage;
    //子女状况
    private SystemDictionaryItem kidCount;
    //学历
    private SystemDictionaryItem educationBackground;
    //住房条件
    private SystemDictionaryItem houseCondition;
    //实名认证状态
    private Long realAuthId;

    public boolean isBidRequestPross(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
    }

    //是否已完成基本信息填写
    public boolean isBasicInfo(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_BASIC_INFO);
    }
    //用户完成实名认证
    public boolean isRealAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_REAL_AUTH);
    }
    //视频认证
    public boolean hasVedioAuth(){
        return BitStatesUtils.hasState(bitState,BitStatesUtils.OP_VEDIO_AUTH);
    }
    //是否满足条件
    public boolean canBorrow(){
        return isBasicInfo() && isRealAuth() && hasVedioAuth();
    }

    public Long getRealAuthId() {
        return realAuthId;
    }

    public void setRealAuthId(Long realAuthId) {
        this.realAuthId = realAuthId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getBitState() {
        return bitState;
    }

    public void setBitState(Long bitState) {
        this.bitState = bitState;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SystemDictionaryItem getIncomeGrade() {
        return incomeGrade;
    }

    public void setIncomeGrade(SystemDictionaryItem incomeGrade) {
        this.incomeGrade = incomeGrade;
    }

    public SystemDictionaryItem getMarriage() {
        return marriage;
    }

    public void setMarriage(SystemDictionaryItem marriage) {
        this.marriage = marriage;
    }

    public SystemDictionaryItem getKidCount() {
        return kidCount;
    }

    public void setKidCount(SystemDictionaryItem kidCount) {
        this.kidCount = kidCount;
    }

    public SystemDictionaryItem getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(SystemDictionaryItem educationBackground) {
        this.educationBackground = educationBackground;
    }

    public SystemDictionaryItem getHouseCondition() {
        return houseCondition;
    }

    public void setHouseCondition(SystemDictionaryItem houseCondition) {
        this.houseCondition = houseCondition;
    }
}
