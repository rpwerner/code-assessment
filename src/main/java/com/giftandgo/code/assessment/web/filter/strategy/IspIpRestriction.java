package com.giftandgo.code.assessment.web.filter.strategy;

import com.giftandgo.code.assessment.configuration.ISPsBlacklistProperties;
import com.giftandgo.code.assessment.external.ipapi.IpApiData;
import org.springframework.beans.factory.annotation.Autowired;

public class IspIpRestriction implements IpRestrictionStrategy {

    @Autowired
    ISPsBlacklistProperties blackListedISPs;

    @Override
    public boolean isIpAllowed(IpApiData ipApiData) {
        return ipApiData.isp() == null || !blackListedISPs.getIsps().contains(ipApiData.isp());
    }
}
