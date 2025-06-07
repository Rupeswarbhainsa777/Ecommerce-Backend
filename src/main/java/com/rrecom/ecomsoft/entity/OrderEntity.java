package com.rrecom.ecomsoft.entity;

import com.rrecom.ecomsoft.io.PaymentDetails;
import com.rrecom.ecomsoft.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(unique = true)
    private String orderId;
    private  String customerName;
    private String phoneNumber;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;

    private LocalDateTime createdAt;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();

    @Embedded
    private PaymentDetails paymentDetails;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;




    @PrePersist
    protected void onCreate(){
                  this.orderId="ORD"+System.currentTimeMillis();
                  this.createdAt=LocalDateTime.now();

    }

}
