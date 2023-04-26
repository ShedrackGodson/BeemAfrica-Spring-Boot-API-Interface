package com.beem.beeminterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Token {
    @Value("${BEEM_API_KEY}")
    private String accessKey;

    @Value("${BEEM_SECRET_KEY}")
    private String secretKey;
}
