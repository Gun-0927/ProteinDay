package com.blue.bluearchive.userpage.dto;

import com.blue.bluearchive.admin.entity.Category;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class UserMainCategoryDto {
    String categoryName;
    List<UserMainLogDataDto> userMainLogDataDtoList;
}
