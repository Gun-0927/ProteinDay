package com.blue.bluearchive.shop.entity;

import com.blue.bluearchive.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter @Setter
@ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="member_idx")
    private Member member;

    public static Cart createCart(Member member)
    {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }


}
