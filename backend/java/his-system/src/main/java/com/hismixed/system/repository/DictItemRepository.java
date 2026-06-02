package com.hismixed.system.repository;

import com.hismixed.system.entity.DictItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictItemRepository extends JpaRepository<DictItem, Long> {

    List<DictItem> findByDictTypeAndStatusOrderBySortOrder(String dictType, String status);

    @Query("SELECT d FROM DictItem d WHERE " +
           "(:dictType IS NULL OR d.dictType = :dictType) AND " +
           "(:keyword IS NULL OR d.dictCode LIKE %:keyword% OR d.dictName LIKE %:keyword%) AND " +
           "(:includeDisabled = true OR d.status = 'ACTIVE')")
    Page<DictItem> findByConditions(
            @Param("dictType") String dictType,
            @Param("keyword") String keyword,
            @Param("includeDisabled") boolean includeDisabled,
            Pageable pageable);

    List<DictItem> findByDictTypeOrderBySortOrder(String dictType);
}
