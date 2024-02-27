package com.fdapn.controller;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {
    private static final int DATA_ROW_INDEX = 1;
    public static CustomerDetails mapExcelToCustomerDetails(Sheet sheet) {
        CustomerDetails customerDetails = new CustomerDetails();

        Row dataRow = sheet.getRow(DATA_ROW_INDEX);

        customerDetails.setSNo((int) getNumericCellValue(dataRow.getCell(0)));
        customerDetails.setAccountId(getStringCellValue(dataRow.getCell(1)));
        customerDetails.setUserId(getStringCellValue(dataRow.getCell(2)));
        customerDetails.setModeOfTrasportation((int) getNumericCellValue(dataRow.getCell(3)));
        customerDetails.setEntryType((int) getNumericCellValue(dataRow.getCell(4)));
        customerDetails.setReferenceIdentifier(getStringCellValue(dataRow.getCell(5)));
        customerDetails.setReferenceIdentifierNo((long) getNumericCellValue(dataRow.getCell(6)));
        customerDetails.setFiler(getStringCellValue(dataRow.getCell(7)));
        customerDetails.setBillType(getStringCellValue(dataRow.getCell(8)));
        customerDetails.setCarrier(getStringCellValue(dataRow.getCell(9)));
        customerDetails.setBillTypeIndicator(getStringCellValue(dataRow.getCell(10)));
        customerDetails.setIssuerCode(getStringCellValue(dataRow.getCell(11)));
        customerDetails.setBillingOfLading(getStringCellValue(dataRow.getCell(12)));
        customerDetails.setPriorNoticeNumber(String.valueOf((int) getNumericCellValue(dataRow.getCell(13))));
        customerDetails.setProductNumber(getStringCellValue(dataRow.getCell(14)));
        customerDetails.setCommercialDesc(getStringCellValue(dataRow.getCell(15)));
        customerDetails.setGovernmentAgencyProcessingCode(getStringCellValue(dataRow.getCell(16)));
        customerDetails.setCommodityDesc(getStringCellValue(dataRow.getCell(17)));
        customerDetails.setCountryOfProduction(getStringCellValue(dataRow.getCell(18)));
        customerDetails.setCountryOfShipment(getStringCellValue(dataRow.getCell(19)));
        customerDetails.setArrivalDate(getStringCellValue(dataRow.getCell(21)));
        customerDetails.setArrivalTime(String.valueOf(getNumericCellValue(dataRow.getCell(22))));
        customerDetails.setPackageTrackingCode(getStringCellValue(dataRow.getCell(23)));
        customerDetails.setPackageTrackingNumber(getStringCellValue(dataRow.getCell(24)));
        customerDetails.setContainerNumber(getStringCellValue(dataRow.getCell(25)));
        List<PartyDetails> partyDetailsList = new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row partyRow = sheet.getRow(i);
            if (partyRow != null) {
                PartyDetails partyDetails = new PartyDetails();
                partyDetails.setPartyType(getStringCellValue(partyRow.getCell(26)));
                partyDetails.setPartyName(getStringCellValue(partyRow.getCell(29)));
                partyDetails.setPartyIdentifierType(getStringCellValue(partyRow.getCell(28)));
                partyDetails.setPartyIdentifierNumber(getStringCellValue(partyRow.getCell(27)));
                partyDetails.setAddress1(getStringCellValue(partyRow.getCell(30)));
                partyDetails.setAddress2(getStringCellValue(partyRow.getCell(31)));
                partyDetails.setApartmentOrSuiteNo(getStringCellValue(partyRow.getCell(32)));
                partyDetails.setCity(getStringCellValue(partyRow.getCell(33)));
                partyDetails.setStateOrProvince(getStringCellValue(partyRow.getCell(34)));
                partyDetails.setCountry(getStringCellValue(partyRow.getCell(35)));
                partyDetails.setPostalCode(getStringCellValue(partyRow.getCell(36)));
                partyDetails.setContactPerson(getStringCellValue(partyRow.getCell(37)));
                partyDetails.setTelephoneNumber(String.valueOf(getNumericCellValue(partyRow.getCell(38))));
                partyDetails.setEmail(getStringCellValue(partyRow.getCell(39)));
                partyDetailsList.add(partyDetails);
            }
        }
        customerDetails.setPartyDetails(partyDetailsList);
        customerDetails.setBaseQuantity((int) getNumericCellValue(dataRow.getCell(40)));
        customerDetails.setBaseUOM(getStringCellValue(dataRow.getCell(41)));
        customerDetails.setPackagingQualifier((int) getNumericCellValue(dataRow.getCell(42)));
        customerDetails.setQuantity((int) getNumericCellValue(dataRow.getCell(43)));
        customerDetails.setUOM(getStringCellValue(dataRow.getCell(44)));
        customerDetails.setAffirmationComplianceCode(getStringCellValue(dataRow.getCell(45)));
        customerDetails.setAffirmationComplianceQualifier(getStringCellValue(dataRow.getCell(46)));
        customerDetails.setEnd(getStringCellValue(dataRow.getCell(47)));
        return customerDetails;
    }

    private static String getStringCellValue(Cell cell) {
        return cell == null ? "" : cell.getStringCellValue();
    }

    private static double getNumericCellValue(Cell cell) {
        return cell == null ? 0 : cell.getNumericCellValue();
    }
}
