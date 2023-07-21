package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_food")
public class CategoryFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_food_id")
    private int categoryFoodId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


}