package com.blue.bluearchive.report_shop.entity;

import com.blue.bluearchive.shop.entity.BaseEntity;
import com.blue.bluearchive.shop.entity.Item;
import com.blue.bluearchive.shop.entity.Review;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reportShop")
@Getter
@Setter
public class ReportShop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportShop_id")
    private int reportId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @ManyToOne
    @JoinColumn(name = "inquiry_answer_id")
    private InquiryAnswer inquiryAnswer;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "target_created_by")
    private String targetCreatedBy;

    @Column(name = "report_content", length = 300, nullable = false)
    private String reportContent;

    @Column(name = "report_category", length = 30, nullable = false)
    private String reportCategory;

}
