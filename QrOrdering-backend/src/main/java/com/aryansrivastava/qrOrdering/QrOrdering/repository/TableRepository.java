package com.aryansrivastava.qrOrdering.QrOrdering.repository;

import com.aryansrivastava.qrOrdering.QrOrdering.model.RestTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository <RestTable,String> {

    @Query("SELECT rt FROM RestTable rt WHERE rt.tableNumber = ?1")
    RestTable getById(String tableNo);
}
