package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.domain.result.IpRestrictionResult;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;

public class NoIpRestriction implements IpRestrictionStrategy {

    @Override
    public IpRestrictionResult validateIpAllowance(IpApiData ipApiData) {
        return IpRestrictionResult.allowed();
    }
}
