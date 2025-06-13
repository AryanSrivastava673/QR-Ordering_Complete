package com.aryansrivastava.qrOrdering.QrOrdering.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tables")
public class RestTable {
    @Id
    private String tableNumber;

    private String qrCodeUrl;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}

