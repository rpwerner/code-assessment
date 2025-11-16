package com.giftandgo.code.assessment.web.filter.strategy;

import com.giftandgo.code.assessment.external.ipapi.IpApiData;
import org.springframework.stereotype.Service;

@Service
public interface IpRestrictionStrategy {
    boolean isIpAllowed(IpApiData ipApiData);
}
