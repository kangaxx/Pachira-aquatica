package com.example.pachiraaquatica.controller;

import com.example.pachiraaquatica.model.KLine;
import com.example.pachiraaquatica.service.KLineService;
import com.example.pachiraaquatica.util.KLineConverter;
import com.example.pachiraaquatica.viewmodel.KLineViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kline")
public class KLineController {
    @Autowired
    private KLineService kLineService;

    /**
     * 获取所有K线数据
     */
    @GetMapping
    public List<KLineViewModel> getAllKLines() {
        return KLineConverter.toViewModelList(kLineService.getAllKLines());
    }

    /**
     * 获取最新的N条K线数据
     */
    @GetMapping("/latest/{count}")
    public List<KLineViewModel> getLatestKLines(@PathVariable int count) {
        return KLineConverter.toViewModelList(kLineService.getLatestKLines(count));
    }

    /**
     * 手动生成一个K线数据
     */
    @PostMapping("/generate")
    public KLineViewModel generateKLine() {
        KLine kLine = kLineService.generateManualKLine();
        return KLineConverter.toViewModel(kLine);
    }
}