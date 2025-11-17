package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.springframework.stereotype.Service;

@Service
public interface IpRestrictionStrategy {
    IpRestrictionResult validateIpAllowance(IpApiData ipApiData);
}
