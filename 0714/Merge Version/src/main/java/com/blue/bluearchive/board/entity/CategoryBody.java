package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "category_body")
public class CategoryBody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_body_id")
    private int categoryBodyId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


}
