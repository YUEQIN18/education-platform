package com.qy.service.edu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author qinyue
 * @create 2022-10-04 23:45:00
 */
@Slf4j
public class EasyExcelListener extends AnalysisEventListener<DemoData> {

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("表头:{}", headMap);
    }

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        log.info("读取行: {}", demoData);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
