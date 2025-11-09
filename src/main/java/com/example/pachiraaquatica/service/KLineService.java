package com.example.pachiraaquatica.service;

import com.example.pachiraaquatica.controller.WebSocketController;
import com.example.pachiraaquatica.model.KLine;
import com.example.pachiraaquatica.repository.KLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class KLineService {
    @Autowired
    private KLineRepository kLineRepository;

    @Autowired
    private WebSocketController webSocketController;

    private double currentPrice = 100.0; // 初始价格
    private final Random random = new Random();

    /**
     * 每分钟生成一个随机K线数据（仅在交易时间9:00-15:00生成）
     */
    @Scheduled(cron = "0 * * * * ?") // 每分钟的第0秒执行
    public void generateKLine() {
        LocalDateTime now = LocalDateTime.now();
        
        // 检查是否在交易时间内（9:00-15:00）
        int hour = now.getHour();
        if (hour < 9 || hour >= 15) {
            // 非交易时间，不生成数据
            return;
        }
        
        // 如果是9点整，重置当前价格为初始价格
        if (hour == 9 && now.getMinute() == 0) {
            currentPrice = 100.0;
        }
        
        // 随机生成K线数据
        double open = currentPrice;
        double change = (random.nextDouble() - 0.5) * 10; // 价格变动范围：-5到+5
        double close = open + change;
        double high = Math.max(open, close) + random.nextDouble() * 5;
        double low = Math.min(open, close) - random.nextDouble() * 5;
        long volume = random.nextLong(1000000) + 100000; // 成交量：10万到110万

        // 创建K线对象
        KLine kLine = new KLine(now, open, high, low, close, volume);
        
        // 保存到仓库
        kLineRepository.save(kLine);
        
        // 通过WebSocket推送最新K线数据
        webSocketController.sendLatestKLine(kLine);
        
        // 更新当前价格为收盘价
        currentPrice = close;
        
        System.out.println("Generated KLine: " + kLine.getTime() + " - Open: " + kLine.getOpen() + ", High: " + kLine.getHigh() + ", Low: " + kLine.getLow() + ", Close: " + kLine.getClose() + ", Volume: " + kLine.getVolume());
    }

    /**
     * 获取当天9点到当前时间的K线数据
     */
    public List<KLine> getTodayKLines() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today9AM = now.withHour(9).withMinute(0).withSecond(0).withNano(0);
        
        List<KLine> allKLines = kLineRepository.findAll();
        List<KLine> todayKLines = new ArrayList<>();
        
        for (KLine kLine : allKLines) {
            if (kLine.getTime().isAfter(today9AM) && kLine.getTime().isBefore(now)) {
                todayKLines.add(kLine);
            }
        }
        
        return todayKLines;
    }

    /**
     * 获取所有K线数据
     */
    public List<KLine> getAllKLines() {
        return kLineRepository.findAll();
    }

    /**
     * 获取最新的N条K线数据
     */
    public List<KLine> getLatestKLines(int count) {
        return kLineRepository.findLatest(count);
    }

    /**
     * 手动生成一个K线数据（用于测试）
     */
    public KLine generateManualKLine() {
        generateKLine();
        List<KLine> kLines = kLineRepository.findAll();
        return kLines.get(kLines.size() - 1);
    }
}