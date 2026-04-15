package com.aaron.patient_service.exception;

public class PatientIDNotFoundException extends RuntimeException {
    public PatientIDNotFoundException(String message) {
        super(message);
    }
}
