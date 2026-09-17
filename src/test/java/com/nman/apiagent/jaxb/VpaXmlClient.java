package com.nman.apiagent.jaxb;

import java.io.StringWriter;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.nman.apiagent.xml.generated.CreateVpaRequest;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

@Service
public class VpaXmlClient {

    private final RestClient restClient;

    public VpaXmlClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://example.com")
                .build();
    }

    public String createVpa(
            String accountId,
            String vpa,
            boolean setAsPrimary,
            String referenceId) {

        try {

            // 1. Create generated JAXB object
            CreateVpaRequest request =
                    new CreateVpaRequest();

            request.setAccountId(accountId);
            request.setVpa(vpa);
            request.setSetAsPrimary(setAsPrimary);
            request.setReferenceId(referenceId);

            // 2. Convert Java object to XML
            JAXBContext context =
                    JAXBContext.newInstance(
                            CreateVpaRequest.class
                    );

            Marshaller marshaller =
                    context.createMarshaller();

            marshaller.setProperty(
                    Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE
            );

            StringWriter writer =
                    new StringWriter();

            marshaller.marshal(request, writer);

            String xmlRequest = writer.toString();

            // 3. Send XML
            return restClient.post()
                    .uri("/api/vpa")
                    .contentType(MediaType.APPLICATION_XML)
                    .body(xmlRequest)
                    .retrieve()
                    .body(String.class);

        } catch (JAXBException e) {

            throw new IllegalStateException(
                    "Unable to generate XML request",
                    e
            );
        }
    }
}
