package com.rhs.extrato.services;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SheetService {
    boolean validateSheet(MultipartFile file) throws IOException;

    void insertSheet(MultipartFile file) throws IOException;


    String getStringValue(XSSFRow row, int columnIndex);
}
