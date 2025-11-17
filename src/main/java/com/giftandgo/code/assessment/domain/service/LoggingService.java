package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.entity.LogEntry;
import com.giftandgo.code.assessment.domain.persistence.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    @Autowired
    LogEntryRepository logEntryRepository;

    public void saveFileLogging(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }
}