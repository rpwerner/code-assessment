package com.giftandgo.code.assessment.web.filter.strategy;

import com.giftandgo.code.assessment.external.ipapi.IpApiData;

public class NoIpRestriction implements IpRestrictionStrategy {
    @Override
    public boolean isIpAllowed(IpApiData ipApiData) {
        return true;
    }
}
