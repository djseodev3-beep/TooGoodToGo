package com.spring.toogoodtogo.store.repository;

import com.spring.toogoodtogo.store.domain.Store;
import com.spring.toogoodtogo.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findByOwnerId (Long id, Pageable pageable);
    boolean existsByOwnerIdAndName (User user, String name);

}
