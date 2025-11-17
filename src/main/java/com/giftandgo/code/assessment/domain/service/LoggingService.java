package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.entity.LogEntry;
import com.giftandgo.code.assessment.domain.persistence.LogEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoggingService {

    private LogEntryRepository logEntryRepository;

    public void saveFileLogging(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }
}