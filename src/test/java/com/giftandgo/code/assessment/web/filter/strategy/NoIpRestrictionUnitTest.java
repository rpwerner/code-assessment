package com.giftandgo.code.assessment.web.filter.strategy;

import com.giftandgo.code.assessment.external.ipapi.IpApiData;
import com.giftandgo.code.assessment.web.filter.strategy.NoIpRestriction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoIpRestrictionUnitTest {

    @Test
    public void givenNoIpRestriction_thenExpectTrue() {
        NoIpRestriction noIpRestriction = new NoIpRestriction();
        assertTrue(noIpRestriction.isIpAllowed(new IpApiData("GB", "Dummy ISP")));
    }
}
