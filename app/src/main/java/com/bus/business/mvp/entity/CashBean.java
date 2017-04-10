package com.bus.business.mvp.entity;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/17
 */

public class CashBean {

    private String cashType;
    private String pledgeCode;
    private String replayCode;
    private String loanCode;
    private String loanLimit;

    public String getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public String getPledgeCode() {
        return pledgeCode;
    }

    public void setPledgeCode(String pledgeCode) {
        this.pledgeCode = pledgeCode;
    }

    public String getReplayCode() {
        return replayCode;
    }

    public void setReplayCode(String replayCode) {
        this.replayCode = replayCode;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    @Override
    public String toString() {
        return "CashBean{" +
                "cashType='" + cashType + '\'' +
                ", pledgeCode='" + pledgeCode + '\'' +
                ", replayCode='" + replayCode + '\'' +
                ", loanCode='" + loanCode + '\'' +
                '}';
    }
}
