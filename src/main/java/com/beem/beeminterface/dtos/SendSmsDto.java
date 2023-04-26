package com.beem.beeminterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

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
