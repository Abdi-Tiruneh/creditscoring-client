package com.dxvalley.creditscoring.score.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerInformationResponse {
    @JsonProperty("CustomerInfoResponse") 
    public CustomerInfoResponse CustomerInfoResponse;
}

class CustomerDetail{
    public ArrayList<Email> emails;
    public String firstName;
    public String lastName;
    public String customerType;
    public ArrayList<Street> streets;
    public String roleDisplayName;
    public ArrayList<TownCountry> townCountries;
    public String beneficialOwner;
    public String dateOfBirth;
    public ArrayList<PhoneNumber> phoneNumbers;
    public String maritalStatus;
    public String customer;
}

class CustomerInfo{
    public int lockedAmount;
    public String limitReference;
    public String gender;
    public String activeBranch;
    public String displayName;
    public String categoryName;
    public String productName;
    public double availableBalance;
    public String accountStatus;
    public String expiryDate;
    public double workingBalance;
    public String customerId;
    public String internalAmount;
    public String currencyId;
    public ArrayList<CustomerDetail> customerDetails;
    public String openingDate;
    @JsonProperty("IBAN") 
    public String iBAN;
    public double clearedBalance;
    public String lastInterestCredited;
    public String companyReference;
    public String customerName;
    public String accountId;
    public String availableLimit;
    public double availableFunds;
    public double onlineActualBalance;
    public String categoryId;
    public String maritalStatus;
}

class CustomerInfoResponse{
    // @JsonProperty("ESBHeader") 
    // public ESBHeader eSBHeader;
    @JsonProperty("CustomerInfo") 
    public ArrayList<CustomerInfo> customerInfo;
    // @JsonProperty("ESBStatus") 
    // public ESBStatus eSBStatus;
}

class Email{
    public String email;
}

class ESBHeader{
    public String serviceCode;
    public String channel;
    @JsonProperty("Service_name") 
    public String service_name;
    @JsonProperty("Message_Id") 
    public String message_Id;
}

class ESBStatus{
    @JsonProperty("Status") 
    public String status;
    public String responseCode;
}

class PhoneNumber{
    public String phoneNumber;
}

class Street{
    public String street;
}

class TownCountry{
    public String townCountry;
}

