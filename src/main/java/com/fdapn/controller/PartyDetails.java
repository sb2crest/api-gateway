package com.fdapn.controller;

import lombok.Data;


@Data
public class PartyDetails {
    private String partyType;
    private String partyIdentifierType;
    private String partyIdentifierNumber;
    private String partyName;
    private String address1;
    private String address2;
    private String apartmentOrSuiteNo;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private String contactPerson;
    private String telephoneNumber;
    private String email;

}
