package com.aryansrivastava.qrOrdering.QrOrdering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tableId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private boolean done;
}
