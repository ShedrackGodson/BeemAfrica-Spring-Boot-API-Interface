package com.beem.beeminterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.beem.beeminterface.Authorize.getHeaders;

public class SMS {

    @NonNull
    private String senderId = "INFO";

    private String message;

    private List<Map<String, Object>> recipients = new ArrayList<>();

    private String scheduleTimeStr = "";

    private String baseUrl = "https://apisms.beem.africa/v1/send";

    private String baseBalanceUrl = "https://apisms.beem.africa/v1/vendors/balance?vendor_id=%s";

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public List<Map<String, Object>> getRecipients() {
        return this.recipients;
    }

    public void setRecipients(List<String> recipients) {
        List<Map<String, Object>> recipientsList = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            Map<String, Object> recipientMap = new HashMap<>();
            recipientMap.put("recipient_id", i + 1);
            recipientMap.put("dest_addr", recipients.get(i));
            recipientsList.add(recipientMap);
        }
        this.recipients = recipientsList;
    }

    public void setScheduleTimeStr(@Nullable LocalDateTime localDateTime) {
        if (localDateTime != null) {
            ZoneOffset zoneOffset = ZoneOffset.UTC;
            ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneOffset);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.scheduleTimeStr = formatter.format(zonedDateTime);
        } else {
            this.scheduleTimeStr = "";
        }
    }

    public String getScheduleTimeStr() {
        return this.scheduleTimeStr;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseBalanceUrl() {
        return baseBalanceUrl;
    }

    public void setBaseBalanceUrl(String baseBalanceUrl) {
        this.baseBalanceUrl = baseBalanceUrl;
    }

    public Object sendSms() throws Exception {
        if (getRecipients().isEmpty()) {
            throw new IllegalArgumentException("Recipients can not be empty");
        }

        Map<String, Object> request = new HashMap<>();
        request.put("source_addr", getSenderId());
        request.put("schedule_time", getScheduleTimeStr());
        request.put("encoding", 0);
        request.put("message", this.getMessage());
        request.put("recipients", this.getRecipients());

        MultiValueMap<String, String> headers = getHeaders();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    getBaseUrl(),
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return objectMapper.readValue(responseEntity.getBody(), Map.class);
        } catch (HttpClientErrorException e) {
            throw new Exception("Error sending SMS: " + e, e);
        }
    }

}
