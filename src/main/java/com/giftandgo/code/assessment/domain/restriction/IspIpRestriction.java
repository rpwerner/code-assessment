package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.configuration.ISPsBlacklistProperties;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.springframework.beans.factory.annotation.Autowired;

public class IspIpRestriction implements IpRestrictionStrategy {

    @Autowired
    ISPsBlacklistProperties blackListedISPs;

    @Override
    public IpRestrictionResult validateIpAllowance(IpApiData ipApiData) {
        if(ipApiData.isp() == null
                || !blackListedISPs.getIsps().contains(ipApiData.isp())) {

            return IpRestrictionResult.allowed();
        }

        return IpRestrictionResult.denied("The ISP %s from IP %s is blacklisted"
                .formatted(ipApiData.isp(), ipApiData.ip()));
    }
}
