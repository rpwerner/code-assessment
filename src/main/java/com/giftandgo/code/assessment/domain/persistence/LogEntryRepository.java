package com.giftandgo.code.assessment.domain.persistence;

import com.giftandgo.code.assessment.domain.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, String> {

    List<LogEntry> findByIpAddress(String ip);

}
