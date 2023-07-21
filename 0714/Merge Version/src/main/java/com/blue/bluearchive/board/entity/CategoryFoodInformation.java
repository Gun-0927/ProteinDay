package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_food_information")
public class CategoryFoodInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_food_information_id")
    private int categoryFoodInformationId;

    @Column(name = "category_food_information_name", nullable = false)
    private String categoryFoodInformationName;

    @Column(name = "category_food_information_size", nullable = false)
    private float categoryFoodInformationSize;

    @Column(name = "category_food_information_kcal", nullable = false)
    private float categoryFoodInformationKcal;

    @Column(name = "category_food_information_carb", nullable = false)
    private float categoryFoodInformationCarb;

    @Column(name = "category_food_information_protein", nullable = false)
    private float categoryFoodInformationProtein;

    @Column(name = "category_food_information_fat", nullable = false)
    private float categoryFoodInformationFat;

}
