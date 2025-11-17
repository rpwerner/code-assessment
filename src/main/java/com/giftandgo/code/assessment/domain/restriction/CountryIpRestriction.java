package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.configuration.CountriesBlacklistProperties;
import com.giftandgo.code.assessment.domain.result.IpRestrictionResult;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryIpRestriction implements IpRestrictionStrategy {

    @Autowired
    CountriesBlacklistProperties blackListCountries;

    @Override
    public IpRestrictionResult validateIpAllowance(IpApiData ipApiData) {
        if(ipApiData.countryCode() == null
                || !blackListCountries.getCountries().contains(ipApiData.countryCode())) {

            return IpRestrictionResult.allowed();
        }

        return IpRestrictionResult.denied("The country %s from IP %s is blacklisted"
                .formatted(ipApiData.countryCode(), ipApiData.ip()));
    }
}
