package dk.sunepoulsen.ciber.deployment.tests

import dk.sunepoulsen.ciber.deployment.tests.http.HttpHelper
import dk.sunepoulsen.ciber.deployment.tests.verification.HttpResponseVerificator
import spock.lang.Specification

import java.net.http.HttpRequest

class NexusSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        this.httpHelper = new HttpHelper('http://localhost:21081/service/rest/v1')
        this.httpHelper.waitForService('/status')
    }

    void "Nexus is ready to receive requests"() {
        when: 'Call GET /status'
            HttpRequest httpRequest = httpHelper.newRequestBuilder('/status')
                .GET()
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 200'
            verificator.responseCode(200)
    }
}
