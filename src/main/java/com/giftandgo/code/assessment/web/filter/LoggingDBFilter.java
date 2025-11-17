package com.giftandgo.code.assessment.web.filter;

import com.giftandgo.code.assessment.domain.entity.LogEntry;
import com.giftandgo.code.assessment.domain.service.LoggingService;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
public class LoggingDBFilter extends OncePerRequestFilter {

    private HandlerExceptionResolver resolver;

    private LoggingService loggingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        Instant start = Instant.now();

        try {
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
        Instant finish = Instant.now();
        long timeLapsed = Duration.between(start, finish).toMillis();

        LogEntry.LogEntryBuilder fileLoggingBuilder = LogEntry.builder();
        fileLoggingBuilder.duration(timeLapsed);

        if (request.getSession().getAttribute("CLIENT_IP_DATA") != null) {
            IpApiData ipApiData = (IpApiData) request.getSession().getAttribute("CLIENT_IP_DATA");
            fileLoggingBuilder.ipAddress(ipApiData.ip());
            fileLoggingBuilder.ipProvider(ipApiData.isp());
            fileLoggingBuilder.ipCountry(ipApiData.countryCode());
        }

        fileLoggingBuilder.createdAt(LocalDateTime.ofInstant(start, ZoneOffset.systemDefault()));
        fileLoggingBuilder.uri(request.getRequestURI());
        fileLoggingBuilder.responseCode(response.getStatus());

        loggingService.saveFileLogging(fileLoggingBuilder.build());
    }
}
