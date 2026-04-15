package com.aaron.patient_service.mapper;

import com.aaron.patient_service.dto.PatientRequestDTO;
import com.aaron.patient_service.dto.PatientResponseDTO;
import com.aaron.patient_service.models.Patient;

import java.time.LocalDate;

public class PatientDTOMapper {
    //PatientResponseDTO patientResponseDTO;
    public static PatientResponseDTO toDTO( Patient patient){
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setBirth_date(patient.getDate_of_birth().toString());
        return patientResponseDTO;
    }

    public static Patient toEntity(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegistered_date(LocalDate.parse(patientRequestDTO.getRegistrationDate()));
        return patient;

    }
}
