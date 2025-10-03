package com.example.stockbacktest.service;

import com.example.stockbacktest.model.StockData;
import com.example.stockbacktest.model.MoneyGrowData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataParseService {
    
    @Autowired
    private FileStorageService fileStorageService;
    
    public List<StockData> parseStockData(String market, String filename) {
        List<StockData> stockDataList = new ArrayList<>();
        try {
            Path filePath = fileStorageService.loadFile(market, filename);
            List<String> lines = FileUtils.readLines(filePath.toFile(), "UTF-8");
            
            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("date") || line.startsWith("日期")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    StockData data = new StockData(
                        parts[0].trim(),
                        Double.parseDouble(parts[1].trim()),
                        Double.parseDouble(parts[2].trim()),
                        Double.parseDouble(parts[3].trim()),
                        Double.parseDouble(parts[4].trim()),
                        Double.parseDouble(parts[5].trim())
                    );
                    stockDataList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDataList;
    }
    
    public List<MoneyGrowData> parseMoneyGrowData(String market, String filename) {
        List<MoneyGrowData> moneyGrowList = new ArrayList<>();
        try {
            Path filePath = fileStorageService.loadFile(market, filename);
            List<String> lines = FileUtils.readLines(filePath.toFile(), "UTF-8");
            
            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("date") || line.startsWith("日期")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    MoneyGrowData data = new MoneyGrowData(
                        parts[0].trim(),
                        Double.parseDouble(parts[1].trim())
                    );
                    moneyGrowList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moneyGrowList;
    }
}