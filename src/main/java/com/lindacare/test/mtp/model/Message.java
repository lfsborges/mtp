package com.lindacare.test.mtp.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Calendar;
import java.util.Date;


public class Message {

    private int userId;
    private String currencyFrom;
    private String currencyTo;
    private double amountSell;
    private double amountBuy;
    private double rate;
    private String timePlaced;
    private String originatingCountry;
    private String topic;
    private int partition;
    private long offset;
    private Date created_at;


    public Message() {
        Calendar cal = Calendar.getInstance();
        this.created_at=cal.getTime();
    }

    public int getUserId() {return userId;}

    public String getCurrencyFrom() {return currencyFrom;}

    public String getCurrencyTo() {return currencyTo;}

    public double getAmountSell() {return amountSell;}

    public double getAmountBuy() {return amountBuy;}

    public double getRate() {return rate;}

    public String getTimePlaced() {return timePlaced;}

    public String getOriginatingCountry() {return originatingCountry;}

    public String getTopic() { return topic; }

    public int getPartition() { return partition; }

    public long getOffset() { return offset; }

    @Override
    public String toString() { return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE); }
}
