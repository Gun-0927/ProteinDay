package com.blue.bluearchive.shop_inquiry.entity;

import com.blue.bluearchive.report_shop.entity.ReportShop;
import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inquiry_answer")
@Getter
@Setter
public class InquiryAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inquiry_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @Column(name = "inquiry_answer_content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "inquiryAnswer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportShop> reportShop;

}
