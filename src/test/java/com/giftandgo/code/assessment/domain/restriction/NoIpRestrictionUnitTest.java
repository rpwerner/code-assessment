package com.giftandgo.code.assessment.domain.restriction;

import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoIpRestrictionUnitTest {

    @Test
    public void givenNoIpRestriction_thenExpectTrue() {
        NoIpRestriction noIpRestriction = new NoIpRestriction();
        IpRestrictionResult ipRestrictionResult = noIpRestriction.validateIpAllowance(new IpApiData("123.123.123.123", "GB", "Dummy ISP"));
        assertTrue(ipRestrictionResult.isAllowed());
    }
}
