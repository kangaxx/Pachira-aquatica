package com.example.pachiraaquatica.util;

import com.example.pachiraaquatica.model.KLine;
import com.example.pachiraaquatica.viewmodel.KLineViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class KLineConverter {

    /**
     * 将KLine转换为KLineViewModel
     */
    public static KLineViewModel toViewModel(KLine kLine) {
        if (kLine == null) {
            return null;
        }
        return new KLineViewModel(kLine);
    }

    /**
     * 将KLineViewModel转换为KLine
     */
    public static KLine toModel(KLineViewModel kLineViewModel) {
        if (kLineViewModel == null) {
            return null;
        }
        return kLineViewModel.toModel();
    }

    /**
     * 将KLine列表转换为KLineViewModel列表
     */
    public static List<KLineViewModel> toViewModelList(List<KLine> kLines) {
        if (kLines == null || kLines.isEmpty()) {
            return List.of();
        }
        return kLines.stream()
                .map(KLineConverter::toViewModel)
                .collect(Collectors.toList());
    }

    /**
     * 将KLineViewModel列表转换为KLine列表
     */
    public static List<KLine> toModelList(List<KLineViewModel> kLineViewModels) {
        if (kLineViewModels == null || kLineViewModels.isEmpty()) {
            return List.of();
        }
        return kLineViewModels.stream()
                .map(KLineConverter::toModel)
                .collect(Collectors.toList());
    }
}