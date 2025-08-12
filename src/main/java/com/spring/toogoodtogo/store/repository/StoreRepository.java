package com.spring.toogoodtogo.store.repository;

import com.spring.toogoodtogo.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Long, Store> {
}
