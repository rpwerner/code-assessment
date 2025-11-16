package com.giftandgo.code.assessment;

import com.github.tomakehurst.wiremock.client.WireMock;

public class WiremockStubHelper {

    public void stubIpNotBlackListed() {
        this.stupIpApi("""
                                {
                                    "countryCode": "GB",
                                    "isp": "Virgin Media"
                                }
                                """);
    }

    public void stubIpIsCountryBlackListed(String countryCode) {
        this.stupIpApi("""
                                {
                                    "countryCode": "%s",
                                    "isp": "Virgin Media"
                                }
                                """.formatted(countryCode));
    }

    public void stubIpIsISPBlackListed(String ispName) {
        this.stupIpApi("""
                                {
                                    "countryCode": "GB",
                                    "isp": "%s"
                                }
                                """.formatted(ispName));
    }

    private void stupIpApi(String expectedBody) {
        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/json/.*"))
                .withQueryParam("fields", WireMock.equalTo("countryCode,isp"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedBody)));
    }

}
