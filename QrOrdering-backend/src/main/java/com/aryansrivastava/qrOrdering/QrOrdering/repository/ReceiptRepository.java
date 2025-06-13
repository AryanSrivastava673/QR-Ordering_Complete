package com.aryansrivastava.qrOrdering.QrOrdering.repository;

import com.aryansrivastava.qrOrdering.QrOrdering.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {
}
