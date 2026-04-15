package com.aaron.patient_service.service;

import com.aaron.patient_service.dto.PatientRequestDTO;
import com.aaron.patient_service.dto.PatientResponseDTO;
import com.aaron.patient_service.exception.EmailAlreadyRegisteredException;
import com.aaron.patient_service.exception.PatientIDNotFoundException;
import com.aaron.patient_service.grpc.BillingServiceGRPCClient;
import com.aaron.patient_service.kafka.KafkaProducer;
import com.aaron.patient_service.mapper.PatientDTOMapper;
import com.aaron.patient_service.models.Patient;
import com.aaron.patient_service.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    PatientRepository patientRepository;
    private final BillingServiceGRPCClient billingServiceGRPCClient;
    private final KafkaProducer kafkaProducer;
    public PatientService(PatientRepository patientRepository, BillingServiceGRPCClient billingServiceGRPCClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGRPCClient = billingServiceGRPCClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS =
                patients.stream()
                        .map(PatientDTOMapper::toDTO)
                        .toList();
        return patientResponseDTOS;

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyRegisteredException("Email is already Registered "+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientDTOMapper.toEntity(patientRequestDTO));

        billingServiceGRPCClient.createBillingAccount(patient.getId().toString(),patient.getName(), patient.getEmail());

        kafkaProducer.sendEvent(patient);

        PatientResponseDTO patientResponseDTO = PatientDTOMapper.toDTO(patient);
        return patientResponseDTO;
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){

        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(()-> new PatientIDNotFoundException("Patient with ID {} not found"+id));
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDate_of_birth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setAddress(patientRequestDTO.getAddress());


        if(patientRepository.existsByEmailAndIdNot(patient.getEmail(), id)){
            throw new EmailAlreadyRegisteredException("Email is already Registered "+patientRequestDTO.getEmail());
        }

        patientRepository.save(patient);
        return PatientDTOMapper.toDTO(patient);

    }

    public ResponseEntity<Void> deletePatient(UUID id){
        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
