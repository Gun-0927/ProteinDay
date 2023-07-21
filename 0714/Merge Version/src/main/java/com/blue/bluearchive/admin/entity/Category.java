package com.blue.bluearchive.admin.entity;

import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardImg;
import com.blue.bluearchive.shop.entity.BaseEntity;
import com.blue.bluearchive.userpage.entity.UserCategoryCount;
import com.blue.bluearchive.userpage.entity.UserCategoryLog;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "category")
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "category_name", length = 100)
    private String categoryName;
    @Column(name = "category_boardCnt")
    private int boardCount = 0;
    @Column(name = "category_totalViews")
    private int totalViews = 0;
    @Column(name = "category_totalReports")
    private int totalReports = 0;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategoryCount> userCategoryCount;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategoryLog> userCategoryLog;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> board;


    public static Category toCategoryEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryName(categoryDto.getCategoryName());
        category.setBoardCount(categoryDto.getBoardCount());
        category.setTotalReports(categoryDto.getTotalReports());
        category.setTotalViews(categoryDto.getTotalViews());
        return category;
    }
}