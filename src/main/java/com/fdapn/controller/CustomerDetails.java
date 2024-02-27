package com.fdapn.controller;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CustomerDetails {
    private int sNo;
    private String accountId;
    private String userId;
    private int modeOfTrasportation;
    private int entryType;
    private String referenceIdentifier;
    private Long referenceIdentifierNo;
    private String filer;
    private String billType;
    private String carrier;
    private String billTypeIndicator;
    private String issuerCode;
    private String billingOfLading;
    private String priorNoticeNumber;
    private String productNumber;
    private String commercialDesc;
    private String governmentAgencyProcessingCode;
    private String commodityDesc;
    private String countryOfProduction;
    private String countryOfShipment;
    private int arrivalLocation;
    private String arrivalDate;
    private String arrivalTime;
    private String packageTrackingCode;
    private String packageTrackingNumber;
    private String containerNumber;
    private List<PartyDetails> partyDetails;
    private int baseQuantity;
    private String baseUOM;
    private int packagingQualifier;
    private int quantity;
    private String UOM;
    private String affirmationComplianceCode;
    private String affirmationComplianceQualifier;
    private String end;
}
