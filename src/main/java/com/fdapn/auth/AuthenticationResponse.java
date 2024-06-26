package com.fdapn.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {
  @JsonProperty("user_id")
  private String userId;
  private String uniqueUserIdentifier;
  private String role;
  @JsonProperty("access_token")
  private String accessToken;
  private Boolean isPortDetails;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("token_expired")
  private String tokenExpired;
  @JsonProperty("refreshToken_expired")
  private String refreshTokenExpired;
}
