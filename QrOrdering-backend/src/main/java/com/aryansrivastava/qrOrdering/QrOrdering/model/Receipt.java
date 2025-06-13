package com.aryansrivastava.qrOrdering.QrOrdering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tableNo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReceiptItem> receiptItems;
    private double itemsTotal;
    private double tax;
    private double grandTotal;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.CreationTimestamp  // Hibernate specific annotation
    private Date createdAt;
}
