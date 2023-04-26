package com.beem.beeminterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * A data object to transfer payload when sending SMS
 */
@Data
@AllArgsConstructor
public class SendSmsDto {
    @NonNull
    private String message;

    @NonNull
    private List<String> recipients;

    @NonNull
    private String senderId;

    @NonNull
    private String scheduleTimeStr;
}
