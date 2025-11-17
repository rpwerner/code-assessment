package com.giftandgo.code.assessment.web.filter;

import com.giftandgo.code.assessment.domain.service.IpLookupService;
import com.giftandgo.code.assessment.domain.service.IpRestrictionService;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class IpRestrictionFilter extends OncePerRequestFilter {

    private IpLookupService ipLookupService;

    private IpRestrictionService ipRestrictionService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientIp = retrieveIpAddressFromRequest(request);
        IpApiData ipInformation = ipLookupService.retrieveIpInformation(clientIp);
        request.getSession().setAttribute("CLIENT_IP_DATA", ipInformation);

        ipRestrictionService.validateIpInformation(ipInformation);

        filterChain.doFilter(request, response);
    }

    private String retrieveIpAddressFromRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");

        if (header != null && !header.isBlank()) {
            // Comma separated IPs, always get the first if any
            String[] parts = header.split(",");
            if (parts.length > 0) {
                return parts[0].trim();
            }
        }

        return request.getRemoteAddr();
    }
}