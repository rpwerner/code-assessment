package com.giftandgo.code.assessment.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log_entry", schema = "giftandgo")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String uuid;
    String uri;
    LocalDateTime createdAt;
    Integer responseCode;
    String ipAddress;
    String ipCountry;
    String ipProvider;
    Long duration;
}
