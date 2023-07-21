package com.blue.bluearchive.userpage.entity;

import com.blue.bluearchive.member.entity.Member;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_graph")
public class UserGraphLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_graph_id")
    private int userGraphId;

//    @CreationTimestamp 테스트시에는 주석처리
    @Column(name = "log_time")
    private LocalDateTime logTime;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @Column(name = "user_graph_weight")
    private Double userGraphWeight=0.0;

    @Column(name = "user_graph_bmi")
    private Double userGraphBmi=0.0;

    @Column(name = "user_graph_intake_cal")
    private Double userGraphIntakeCal=0.0;

    @Column(name = "user_graph_subtract_cal")
    private Double userGraphSubtractCal=0.0;

}
