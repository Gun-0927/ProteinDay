package com.blue.bluearchive.admin.dto;

import com.blue.bluearchive.admin.entity.Category;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryDto {  //승훈이 꺼에서 어노테이션만 추가
    private int categoryId;
    private String categoryName;
    // 승훈 코드 추가
    private int boardCount;
    private int totalViews;
    private int totalReports;
    // 승훈 코드 추가
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getCategoryName(), category.getBoardCount(), category.getTotalReports(), category.getTotalViews());
    }

}