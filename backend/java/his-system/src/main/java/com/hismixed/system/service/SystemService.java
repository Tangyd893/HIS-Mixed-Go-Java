package com.hismixed.system.service;

import com.hismixed.system.entity.AuditLog;
import com.hismixed.system.entity.DictItem;
import com.hismixed.system.repository.AuditLogRepository;
import com.hismixed.system.repository.DictItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SystemService {

    @Autowired
    private DictItemRepository dictItemRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    // 字典管理

    public Page<DictItem> listDicts(String dictType, String keyword, boolean includeDisabled, Pageable pageable) {
        return dictItemRepository.findByConditions(dictType, keyword, includeDisabled, pageable);
    }

    public List<DictItem> getDictByType(String dictType) {
        return dictItemRepository.findByDictTypeOrderBySortOrder(dictType);
    }

    public DictItem getDictById(Long id) {
        return dictItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("字典项不存在"));
    }

    @Transactional
    public DictItem createDict(DictItem dictItem) {
        return dictItemRepository.save(dictItem);
    }

    @Transactional
    public DictItem updateDict(Long id, DictItem dictItem) {
        DictItem existing = getDictById(id);
        existing.setDictType(dictItem.getDictType());
        existing.setDictCode(dictItem.getDictCode());
        existing.setDictName(dictItem.getDictName());
        existing.setSortOrder(dictItem.getSortOrder());
        existing.setStatus(dictItem.getStatus());
        existing.setRemark(dictItem.getRemark());
        return dictItemRepository.save(existing);
    }

    @Transactional
    public void deleteDict(Long id) {
        dictItemRepository.deleteById(id);
    }

    // 审计日志

    public Page<AuditLog> getAuditLogs(Long userId, String action, String resourceType,
                                        String startTime, String endTime, Pageable pageable) {
        LocalDateTime start = startTime != null && !startTime.isEmpty() ?
                LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME) : null;
        LocalDateTime end = endTime != null && !endTime.isEmpty() ?
                LocalDateTime.parse(endTime, DateTimeFormatter.ISO_DATE_TIME) : null;

        return auditLogRepository.findByConditions(userId, action, resourceType, start, end, pageable);
    }

    @Transactional
    public AuditLog createAuditLog(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }
}
