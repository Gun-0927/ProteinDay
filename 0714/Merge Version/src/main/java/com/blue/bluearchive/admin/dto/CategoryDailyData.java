package com.blue.bluearchive.admin.dto;


import lombok.Data;

import java.util.List;

@Data
public class CategoryDailyData {

    private String categoryName;
    private List<DailyData> dailyData;
}
