package com.example.pachiraaquatica.controller;

import com.example.pachiraaquatica.model.KLine;
import com.example.pachiraaquatica.service.KLineService;
import com.example.pachiraaquatica.util.KLineConverter;
import com.example.pachiraaquatica.viewmodel.KLineViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private KLineService kLineService;

    /**
     * 发送最新的K线数据到所有订阅者
     */
    public void sendLatestKLine(KLine kLine) {
        KLineViewModel viewModel = KLineConverter.toViewModel(kLine);
        messagingTemplate.convertAndSend("/topic/kline", viewModel);
    }

    /**
     * 客户端请求获取历史K线数据（返回当天9点到当前时间的数据）
     */
    @MessageMapping("/history")
    @SendTo("/topic/history")
    public List<KLineViewModel> getHistory() {
        return KLineConverter.toViewModelList(kLineService.getTodayKLines());
    }

    /**
     * 客户端请求获取最新的N条K线数据
     */
    @MessageMapping("/latest/{count}")
    @SendTo("/topic/latest")
    public List<KLineViewModel> getLatest(int count) {
        return KLineConverter.toViewModelList(kLineService.getLatestKLines(count));
    }
}