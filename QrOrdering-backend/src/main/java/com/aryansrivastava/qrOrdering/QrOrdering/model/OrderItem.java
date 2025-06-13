package com.aryansrivastava.qrOrdering.QrOrdering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private MenuItem menuItem;

    private Integer quantity;
}
