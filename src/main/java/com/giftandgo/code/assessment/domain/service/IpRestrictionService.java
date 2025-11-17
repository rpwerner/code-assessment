package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.restriction.IpRestrictionStrategy;
import com.giftandgo.code.assessment.domain.result.IpRestrictionResult;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import com.giftandgo.code.assessment.web.exception.IpRestrictionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpRestrictionService {

    @Autowired
    List<IpRestrictionStrategy> allValidations;

    public void validateIpInformation(IpApiData ipInformation) {

        for (IpRestrictionStrategy restriction : allValidations) {
            IpRestrictionResult ipRestrictionResult = restriction.validateIpAllowance(ipInformation);
            if (!ipRestrictionResult.isAllowed()) {
                throw new IpRestrictionException(ipRestrictionResult.getReason());
            }
        }
    }
}
