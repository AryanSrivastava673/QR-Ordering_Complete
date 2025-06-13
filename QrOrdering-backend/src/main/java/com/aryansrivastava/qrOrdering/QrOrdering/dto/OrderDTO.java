package com.aryansrivastava.qrOrdering.QrOrdering.dto;

import com.aryansrivastava.qrOrdering.QrOrdering.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long id;
    private String tableId;
    private List<OrderItemDTO> orderItems;
    private boolean done;
}
