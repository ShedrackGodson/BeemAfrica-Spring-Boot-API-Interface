package com.beem.beeminterface;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class BeemInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeemInterfaceApplication.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {

            try {
                SMS sms = new SMS();
                sms.setSenderId("BIONET");
                sms.setMessage("This wonderful man! Just from JRE!");

                List<String> recipients = new ArrayList<>();
                recipients.add("255762547915");
                recipients.add("255768209424");

                sms.setRecipients(recipients);
                sms.setScheduleTimeStr(null);
                Authorize.getEncodedToken();

                sms.sendSms();
            } catch (Exception e) {
                throw new IllegalArgumentException("Unexpected exception occurred.");
            }
        };
    }

}
