package com.aryansrivastava.qrOrdering.QrOrdering.repository;

import com.aryansrivastava.qrOrdering.QrOrdering.model.ReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem,Long> {
}
