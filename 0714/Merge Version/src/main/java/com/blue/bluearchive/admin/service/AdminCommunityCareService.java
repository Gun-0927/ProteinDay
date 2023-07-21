package com.blue.bluearchive.admin.service;


import com.blue.bluearchive.admin.dto.CategoryDailyData;
import com.blue.bluearchive.admin.dto.DailyData;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.repository.AdminBoardRepository;
import com.blue.bluearchive.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommunityCareService {
    private final CategoryRepository categoryRepository;
    private final AdminBoardRepository boardRepository;

    public List<CategoryDailyData> getCategoryDailyData() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDailyData> categoryDailyDataList = new ArrayList<>();

        for (Category category : categories) {
            CategoryDailyData categoryDailyData = new CategoryDailyData();
            categoryDailyData.setCategoryName(category.getCategoryName());
            log.info(categoryDailyData.getCategoryName());
            List<Object[]> dailyData = boardRepository.findDailyDataByCategoryId(category.getCategoryId());
            List<DailyData> formattedDailyData = formatDailyData(dailyData);
            categoryDailyData.setDailyData(formattedDailyData);
            log.info(String.valueOf(categoryDailyData.getDailyData()));
            categoryDailyDataList.add(categoryDailyData);
        }

        return categoryDailyDataList;
    }

    private List<DailyData> formatDailyData(List<Object[]> dailyData) {
        List<DailyData> formattedData = new ArrayList<>();

        for (Object[] data : dailyData) {
            java.sql.Date sqlDate = (java.sql.Date) data[0];
            LocalDate date = sqlDate.toLocalDate();
            Long views = (Long) data[1];
            Long boardCount = (Long) data[2];

            DailyData dailyDataEntry = new DailyData();
            dailyDataEntry.setDate(date);
            dailyDataEntry.setViews(views.intValue());
            dailyDataEntry.setBoardCount(boardCount.intValue());

            formattedData.add(dailyDataEntry);
        }

        return formattedData;
    }
}
