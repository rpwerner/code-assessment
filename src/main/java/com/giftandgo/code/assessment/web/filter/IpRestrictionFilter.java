package com.giftandgo.code.assessment.web.filter;

import com.giftandgo.code.assessment.external.ipapi.IpApiData;
import com.giftandgo.code.assessment.external.ipapi.IpAPIService;
import com.giftandgo.code.assessment.web.exception.IpRestrictionException;
import com.giftandgo.code.assessment.web.filter.strategy.IpRestrictionStrategy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Component
public class IpRestrictionFilter extends OncePerRequestFilter {

    @Autowired
    IpAPIService ipAPIService;

    @Autowired
    List<IpRestrictionStrategy> ipRestrictions;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        String header = request.getHeader("X-Forwarded-For");
        String clientIp = header != null && !header.isBlank()
                ? header.split(",")[0].trim()
                : request.getRemoteAddr();

        try {

            IpApiData ipInformation = ipAPIService.getIpInformationByIp(clientIp);

            for (IpRestrictionStrategy restriction : ipRestrictions) {
                if (!restriction.isIpAllowed(ipInformation)) {
                    throw new IpRestrictionException("The IP %s is not allowed in the system".formatted(clientIp));
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}