package com.aaron.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGRPCClient {

    private final BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub;

    public BillingServiceGRPCClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.server.grpc.port:9001}") int serverPort
    ){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress,serverPort).usePlaintext().build();
        log.info("Connected to server on port {} and address{}", serverPort, serverAddress);
        billingServiceBlockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(  String patientId,
    String name, String email){
        BillingRequest request = BillingRequest.newBuilder()
                .setEmail(email)
                .setName(name)
                .setPatientId(patientId)
                .build();
        BillingResponse response = billingServiceBlockingStub
                .createBillingAccount(request);
        log.info("Create billing account response: {}", response);
        return response;
    }
}
