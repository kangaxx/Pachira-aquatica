package com.example.pachiraaquatica.repository;

import com.example.pachiraaquatica.model.KLine;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class KLineRepository {
    // 使用线程安全的列表存储K线数据
    private final List<KLine> kLineList = new CopyOnWriteArrayList<>();

    /**
     * 保存K线数据
     */
    public void save(KLine kLine) {
        kLineList.add(kLine);
    }

    /**
     * 获取所有K线数据
     */
    public List<KLine> findAll() {
        return new ArrayList<>(kLineList);
    }

    /**
     * 获取最新的N条K线数据
     */
    public List<KLine> findLatest(int count) {
        int size = kLineList.size();
        if (size <= count) {
            return new ArrayList<>(kLineList);
        }
        return new ArrayList<>(kLineList.subList(size - count, size));
    }

    /**
     * 清空所有K线数据
     */
    public void clear() {
        kLineList.clear();
    }

    /**
     * 获取当天9点到当前时间的K线数据
     */
    public List<KLine> findToday9AMToNow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today9AM = now.withHour(9).withMinute(0).withSecond(0).withNano(0);
        
        List<KLine> result = new ArrayList<>();
        for (KLine kLine : kLineList) {
            if (kLine.getTime().isAfter(today9AM) && kLine.getTime().isBefore(now)) {
                result.add(kLine);
            }
        }
        return result;
    }
}