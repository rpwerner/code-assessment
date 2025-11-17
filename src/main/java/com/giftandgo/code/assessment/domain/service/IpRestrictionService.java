package com.giftandgo.code.assessment.domain.service;

import com.giftandgo.code.assessment.domain.restriction.IpRestrictionResult;
import com.giftandgo.code.assessment.domain.restriction.IpRestrictionStrategy;
import com.giftandgo.code.assessment.external.client.ipapi.IpApiData;
import com.giftandgo.code.assessment.web.exception.IpRestrictionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IpRestrictionService {

    private List<IpRestrictionStrategy> allValidations;

    public void validateIpInformation(IpApiData ipInformation) {
        for (IpRestrictionStrategy restriction : allValidations) {
            IpRestrictionResult ipRestrictionResult = restriction.validateIpAllowance(ipInformation);
            if (!ipRestrictionResult.isAllowed()) {
                throw new IpRestrictionException(ipRestrictionResult.getReason());
            }
        }
    }
}
