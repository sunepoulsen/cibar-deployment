package dk.sunepoulsen.cibar.deployment.tests

import dk.sunepoulsen.cibar.deployment.tests.http.HttpHelper
import dk.sunepoulsen.cibar.deployment.tests.verification.HttpResponseVerificator
import spock.lang.Specification
import spock.lang.Unroll

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

    void "Nexus is secure"() {
        when: 'Call GET /repositories/maven/hosted/maven-snapshots'
            HttpRequest httpRequest = httpHelper.newRequestBuilder('/repositories/maven/hosted/maven-snapshots')
                .GET()
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 403'
            verificator.responseCode(403)
    }

    @Unroll
    void "Has #repositoryType repository: '#repositoryName'"() {
        when: "Call GET /repositories/${repositoryType}/hosted${repositoryName}"
            HttpRequest httpRequest = httpHelper.newRequestBuilder( "/repositories/${repositoryType}/hosted/${repositoryName}")
                .GET()
                .header('Authorization', 'Basic YWRtaW46anVraWxvOTA=')
                .build()

            HttpResponseVerificator verificator = httpHelper.sendRequest(httpRequest)

        then: 'Response Code is 200'
            verificator.responseCode(200)

        then:
            def body = verificator.bodyAsJson()
            body.name == repositoryName
            body.online == true

        where:
            repositoryType | repositoryName
            'maven'        | 'maven-snapshots'
            'maven'        | 'maven-releases'
            'docker'       | 'docker-snapshots'
            'docker'       | 'docker-releases'
            'docker'       | 'docker-cibar'
    }
}
