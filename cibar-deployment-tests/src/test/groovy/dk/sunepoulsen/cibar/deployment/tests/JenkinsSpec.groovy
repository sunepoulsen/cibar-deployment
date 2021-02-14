package dk.sunepoulsen.cibar.deployment.tests

import dk.sunepoulsen.cibar.deployment.tests.http.HttpHelper
import dk.sunepoulsen.cibar.deployment.tests.verification.HttpResponseVerificator
import spock.lang.Specification

import java.net.http.HttpRequest

class JenkinsSpec extends Specification {

    private HttpHelper httpHelper

    void setup() {
        this.httpHelper = new HttpHelper('http://localhost:21080')
        this.httpHelper.waitForService('')
    }

    void "Jenkins Web interface is secure"() {
        when: 'Call GET /'
            HttpRequest httpRequest = httpHelper.newRequestBuilder('')
                .GET()
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 403'
            verificator.responseCode(403)
    }

    void "Jenkins Web interface can be accessed with authorized user"() {
        when: 'Call GET /'
            HttpRequest httpRequest = httpHelper.newRequestBuilder('')
                .GET()
                .header('Authorization', 'Basic c3VuZXBvdWxzZW46anVraWxvOTA=')
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 200'
            verificator.responseCode(200)
    }
}
