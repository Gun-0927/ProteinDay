package com.blue.bluearchive.admin.entity;


import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "category_statistics")
public class AdminCommunityCare{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_id")
    private Long statisticsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "board_count")
    private Integer boardCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "total_views")
    private Integer totalViews;

    @Column(name = "log_time")
    private LocalDateTime logTime;
}
