package com.giftandgo.code.assessment.domain.persistence;

import com.giftandgo.code.assessment.domain.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, String> {

    List<LogEntry> findByIpAddress(String ip);

}
