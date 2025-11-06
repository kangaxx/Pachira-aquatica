package com.example.pachiraaquatica.viewmodel;

import com.example.pachiraaquatica.model.KLine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KLineViewModel {
    private String time;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;

    // 时间格式化器
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 构造函数，从Model转换为ViewModel
    public KLineViewModel(KLine kLine) {
        this.time = kLine.getTime().format(TIME_FORMATTER);
        this.open = kLine.getOpen();
        this.high = kLine.getHigh();
        this.low = kLine.getLow();
        this.close = kLine.getClose();
        this.volume = kLine.getVolume();
    }

    // Getters and Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    // 将ViewModel转换为Model
    public KLine toModel() {
        LocalDateTime time = LocalDateTime.parse(this.time, TIME_FORMATTER);
        return new KLine(time, open, high, low, close, volume);
    }
}