package com.giftandgo.code.assessment;

import com.github.tomakehurst.wiremock.client.WireMock;

public class WiremockStubHelper {

    public void stubIpNotBlackListed(String ip) {
        this.stupIpApi("""
                                {
                                    "query": "%s",
                                    "countryCode": "GB",
                                    "isp": "Virgin Media"
                                }
                                """.formatted(ip));
    }

    public void stubIpIsCountryBlackListed(String countryCode, String ip) {
        this.stupIpApi("""
                                {
                                    "query": "%s",
                                    "countryCode": "%s",
                                    "isp": "Virgin Media"
                                }
                                """.formatted(ip, countryCode));
    }

    public void stubIpIsISPBlackListed(String ispName, String ip) {
        this.stupIpApi("""
                                {
                                    "query": "%s",
                                    "countryCode": "GB",
                                    "isp": "%s"
                                }
                                """.formatted(ip, ispName));
    }

    private void stupIpApi(String expectedBody) {
        WireMock.stubFor(WireMock.get(WireMock.urlPathMatching("/json/.*"))
                .withQueryParam("fields", WireMock.equalTo("query,countryCode,isp"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(expectedBody)));
    }

}
