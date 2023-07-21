package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_place_img")
public class CategoryPlaceImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_place_img_id")
    private int categoryPlaceImgId;

    @ManyToOne
    @JoinColumn(name = "category_place_id")
    private CategoryPlace categoryPlace;

    @Column(name = "category_place_img_url", length = 200, nullable = false)
    private String categoryPlaceImgUrl;


}