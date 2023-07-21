package com.blue.bluearchive.userpage.entity;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_category_log")
public class UserCategoryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_count_id")
    private int userCategoryLogId;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_count")
    private Integer categoryCount=0;

    @CreationTimestamp
    @Column(name = "log_time")
    private LocalDateTime logTime;
}