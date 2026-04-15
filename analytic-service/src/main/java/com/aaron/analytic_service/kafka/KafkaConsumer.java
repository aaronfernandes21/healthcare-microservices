package com.aaron.analytic_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.internals.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytic-service")
    public void consumeEvent(byte[] event){
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Patient Event received:{}", patientEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error("Deserializing error while working on event", e);
        }
    }
}
