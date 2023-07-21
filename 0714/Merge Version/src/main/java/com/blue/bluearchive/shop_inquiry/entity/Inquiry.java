package com.blue.bluearchive.shop_inquiry.entity;

import com.blue.bluearchive.report_shop.entity.ReportShop;
import com.blue.bluearchive.shop.entity.BaseEntity;
import com.blue.bluearchive.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inquiry")
@Getter
@Setter
public class Inquiry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "inquiry_content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquiryAnswer> inquiryAnswer;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportShop> reportShop;

}
