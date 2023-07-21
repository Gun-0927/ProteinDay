package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_body_img")
public class CategoryBodyImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_body_img_id")
    private int categoryBodyImgId;

    @ManyToOne
    @JoinColumn(name = "category_body_id")
    private CategoryBody categoryBody;

    @Column(name = "category_body_img_url", length = 200, nullable = false)
    private String categoryBodyImgUrl;

    // Constructors, getters, and setters

    // ...
}