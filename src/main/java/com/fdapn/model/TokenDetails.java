package com.fdapn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDetails {
    private String token;
    private String expirationTime;
}
