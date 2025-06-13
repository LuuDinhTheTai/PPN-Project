package com.utc.ppnproject.repository;

import com.utc.ppnproject.entity.Receipt;
import com.utc.ppnproject.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends BaseRepository<Receipt, String> {
}
