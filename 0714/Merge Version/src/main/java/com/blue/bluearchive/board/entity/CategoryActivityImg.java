package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_activity_img")
public class CategoryActivityImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_activity_img_id")
    private int categoryActivityImgId;

    @Column(name = "category_activity_img_url", length = 200)
    private String categoryActivityImgUrl;

    @ManyToOne
    @JoinColumn(name = "category_activity_id")
    private CategoryActivity categoryActivity;


}