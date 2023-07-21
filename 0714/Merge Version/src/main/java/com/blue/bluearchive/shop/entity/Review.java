package com.blue.bluearchive.shop.entity;

import com.blue.bluearchive.report_shop.entity.ReportShop;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "review")
@Getter
@Setter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "review_nickname", nullable = false)
    private String nickName;

    @Column(name = "review_content", nullable = false)
    private String content;

    @Column(name = "review_star", nullable = false)
    private String star;

    @Column(name = "review_reports_count")
    private int reportsCount;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImg> reviewImgs;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportShop> reportShop;


}
