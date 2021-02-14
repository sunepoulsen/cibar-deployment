package dk.sunepoulsen.cibar.deployment.tests.http

class ServiceNotAvailableException extends Exception {
    ServiceNotAvailableException(String message) {
        super(message)
    }
}
