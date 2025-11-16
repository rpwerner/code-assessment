package com.giftandgo.code.assessment.web.filter.strategy;

import com.giftandgo.code.assessment.configuration.CountriesBlacklistProperties;
import com.giftandgo.code.assessment.external.ipapi.IpApiData;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryIpRestriction implements IpRestrictionStrategy {
    @Autowired
    CountriesBlacklistProperties blackListCountries;

    @Override
    public boolean isIpAllowed(IpApiData ipApiData) {
        return ipApiData.countryCode() == null
                || !blackListCountries.getCountries().contains(ipApiData.countryCode());
    }
}
