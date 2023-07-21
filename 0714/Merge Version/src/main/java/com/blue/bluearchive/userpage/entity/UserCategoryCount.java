package com.blue.bluearchive.userpage.entity;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Data
@Table(name = "user_category_count")
public class UserCategoryCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_count_id")
    private int userCategoryCountId;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "category_pre_count")
    private Integer categoryPreCount=0;

    @Column(name = "category_all_count")
    private Integer categoryAllCount=0;
}