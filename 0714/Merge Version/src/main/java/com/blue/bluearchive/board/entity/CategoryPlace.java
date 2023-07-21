package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "category_place")
public class CategoryPlace {
    @Id
    @Column(name = "category_place_id")
    private int categoryPlaceId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
