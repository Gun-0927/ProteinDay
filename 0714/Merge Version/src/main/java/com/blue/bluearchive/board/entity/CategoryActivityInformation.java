package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
@Table(name = "category_activity_information")
public class CategoryActivityInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_activity_information_id")
    private int categoryActivityInformationId;

    @Column(name = "category_activity_information_name", nullable = false)
    private String categoryActivityInformationName;

    @Column(name = "category_activity_information_met", nullable = false)
    private float categoryActivityInformationMet;

}