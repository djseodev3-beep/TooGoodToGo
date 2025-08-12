package com.spring.toogoodtogo.store.repository;

import com.spring.toogoodtogo.store.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByOwnerId (Long id, Pageable pageable);
    boolean existsByOwnerIdAndName (Long id,String name);

}
