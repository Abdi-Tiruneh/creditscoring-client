package com.dxvalley.creditscoring.xxxx;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserInfoResponse {
   private UserInfo userInfo;
}

@Data
class UserInfo {
   private String fullName;
   private String gender;
   private String dateOfBirth;
   private String email;
   private String imageUrl;
   private String languageCode;
   @JsonProperty("Status")
   private String Status;
}

@Data
class Account{
  private String isMain;
  private String accountName;
}
