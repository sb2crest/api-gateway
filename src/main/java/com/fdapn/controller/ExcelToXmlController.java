package com.fdapn.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;

@RestController
public class ExcelToXmlController {

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertExcelToXml(@RequestParam("file") MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream);
            writer.writeStartDocument();
            writer.writeStartElement("data");
            Sheet sheet = workbook.getSheetAt(0); // Assuming first sheet

            Row headerRow = sheet.getRow(0); // Get header row
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row currentRow = sheet.getRow(i);
                writer.writeStartElement("row");
                for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
                    String columnName = headerRow.getCell(j).getStringCellValue().trim();
                    String cellValue = currentRow.getCell(j) != null ?
                            currentRow.getCell(j).toString() : "";
                    writer.writeStartElement(formatXmlTagName(columnName));
                    writer.writeCharacters(cellValue);
                    writer.writeEndElement();
                }
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.close();
            byte[] xmlBytes = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=output.xml")
                    .body(xmlBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    private String formatXmlTagName(String tagName) {
        return tagName.replaceAll("[^a-zA-Z0-9_]", "_");
    }
}
