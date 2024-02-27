package com.fdapn.controller;

import javax.xml.bind.*;
import java.io.StringWriter;
public class XmlConverter {
    public static String convertToXml(CustomerDetails customerDetails) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CustomerDetails.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(customerDetails, sw);
        return sw.toString();
    }
}
