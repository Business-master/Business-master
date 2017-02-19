package com.bus.business.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/17
 */

public class DropBean {


    /**
     * utime : null
     * pledgeCode : 房贷
     * lendingTime : 333
     * replayCode : 到期还款
     * cashType : 1
     * bankName : aaaa
     * logoUrl : /cashProfessor/64ce0fdf061c4fd488f1d71efcdcef9d.jpg
     * ctime : 1487309902000
     * loanIntroduction : 33333
     * id : 4
     * loanCode : 6个月
     * productDesp :
     * productName : aaa
     * cashRate : 22323
     */

    private Object utime;
    private String pledgeCode;
    private int lendingTime;
    private String replayCode;
    private int cashType;
    private String bankName;
    private String logoUrl;
    private long ctime;
    private String loanIntroduction;
    private int id;
    private String loanCode;
    private String productDesp;
    private String productName;
    private int cashRate;

    public Object getUtime() {
        return utime;
    }

    public void setUtime(Object utime) {
        this.utime = utime;
    }

    public String getPledgeCode() {
        return pledgeCode;
    }

    public void setPledgeCode(String pledgeCode) {
        this.pledgeCode = pledgeCode;
    }

    public int getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(int lendingTime) {
        this.lendingTime = lendingTime;
    }

    public String getReplayCode() {
        return replayCode;
    }

    public void setReplayCode(String replayCode) {
        this.replayCode = replayCode;
    }

    public int getCashType() {
        return cashType;
    }

    public void setCashType(int cashType) {
        this.cashType = cashType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getLoanIntroduction() {
        return loanIntroduction;
    }

    public void setLoanIntroduction(String loanIntroduction) {
        this.loanIntroduction = loanIntroduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getProductDesp() {
        return productDesp;
    }

    public void setProductDesp(String productDesp) {
        this.productDesp = productDesp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCashRate() {
        return cashRate;
    }

    public void setCashRate(int cashRate) {
        this.cashRate = cashRate;
    }

    @Override
    public String toString() {
        return "DropBean{" +
                "utime=" + utime +
                ", pledgeCode='" + pledgeCode + '\'' +
                ", lendingTime=" + lendingTime +
                ", replayCode='" + replayCode + '\'' +
                ", cashType=" + cashType +
                ", bankName='" + bankName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", ctime=" + ctime +
                ", loanIntroduction='" + loanIntroduction + '\'' +
                ", id=" + id +
                ", loanCode='" + loanCode + '\'' +
                ", productDesp='" + productDesp + '\'' +
                ", productName='" + productName + '\'' +
                ", cashRate=" + cashRate +
                '}';
    }
}
