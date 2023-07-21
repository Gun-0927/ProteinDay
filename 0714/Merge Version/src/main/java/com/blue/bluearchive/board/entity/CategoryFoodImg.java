package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_food_img")
public class CategoryFoodImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_food_img_id")
    private int categoryFoodImgId;

    @ManyToOne
    @JoinColumn(name = "category_food_id")
    private CategoryFood categoryFood;

    @Column(name = "category_food_img_url", length = 200, nullable = false)
    private String categoryFoodImgUrl;
}
