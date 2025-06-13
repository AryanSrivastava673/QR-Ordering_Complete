package com.aryansrivastava.qrOrdering.QrOrdering.repository;

import com.aryansrivastava.qrOrdering.QrOrdering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
