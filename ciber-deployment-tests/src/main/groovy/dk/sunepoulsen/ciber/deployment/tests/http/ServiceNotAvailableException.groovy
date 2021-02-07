package dk.sunepoulsen.ciber.deployment.tests.http

class ServiceNotAvailableException extends Exception {
    ServiceNotAvailableException(String message) {
        super(message)
    }
}
