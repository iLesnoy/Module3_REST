package com.epam.esm.gifts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long orderId;

    @Column(name = "purchase_time", nullable = false, updatable = false)
    private LocalDateTime purchaseTime;

    @Column(name = "order_cost", nullable = false, updatable = false)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="gift_certificate"
            ,joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "gift_certificate_id",referencedColumnName = "id"))
    private List<GiftCertificate> certificateList = new ArrayList<>();


    @PrePersist
    private void prePersist(){
        purchaseTime = LocalDateTime.now();
        cost =  certificateList.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ONE,BigDecimal::add);
    }
}
