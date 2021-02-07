package dk.sunepoulsen.ciber.deployment.tests.http

import dk.sunepoulsen.ciber.deployment.tests.verification.HttpResponseVerificator
import groovy.util.logging.Slf4j

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Slf4j
class HttpHelper {

    static final Long DEFAULT_TIMEOUT = 60000L
    static final Long DEFAULT_SLEEP = 500L

    private String baseUrl
    protected HttpClient httpClient

    HttpHelper(String baseUrl) {
        this.baseUrl = baseUrl
        initHttpClient()
    }

    void initHttpClient() {
        initHttpClient(Duration.ofSeconds(30L))
    }

    void initHttpClient(Duration timeout) {
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(timeout)
            .build()
    }

    HttpRequest.Builder newRequestBuilder(String url) {
        return HttpRequest.newBuilder()
            .uri(new URI(this.baseUrl + url))
            .timeout(Duration.ofSeconds(30L))

    }

    HttpResponseVerificator sendRequest(HttpRequest httpRequest) {
        return new HttpResponseVerificator(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()))
    }

    void waitForService( String url ) {
        long start = System.currentTimeMillis()

        while( ( System.currentTimeMillis() - start ) < DEFAULT_TIMEOUT ) {
            try {
                log.debug( "Waiting for service to be available" )
                if( isAvailable(url) ) {
                    long spent = System.currentTimeMillis() - start
                    log.debug( "Service available after {}ms", spent )
                    return
                }
            }
            catch( Exception ex ) {
                log.debug( "Exception from service: {}", ex.getMessage() )
            }

            try {
                Thread.sleep( DEFAULT_SLEEP )
            }
            catch( InterruptedException ex ) {
                log.debug( "Unable to sleep thread: " + ex.getMessage(), ex )
            }
        }

        throw new ServiceNotAvailableException("The service at ${this.baseUrl + url} is not available after ${DEFAULT_TIMEOUT} ms")
    }

    private boolean isAvailable(String url) {
        HttpRequest httpRequest = newRequestBuilder(url)
            .GET()
            .build()

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        return httpResponse.statusCode() == 200
    }

}
