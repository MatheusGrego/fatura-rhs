package com.rhs.extrato.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@UtilityClass
@Slf4j
public class FileUtils {
    public boolean validateSheet(MultipartFile file) throws IOException {
        String mimeType = new Tika().detect(file.getBytes());

        if (file.isEmpty()) {
            log.error("Arquivo vazio...");
            throw new EmptyFileException();
        }
        if (!mimeType.equals("application/x-tika-ooxml")) {
            log.error("Tipo de arquivo não permitido");
            throw new MultipartException("Tipo de arquivo não permitido");
        }
        return true;
    }

    public String getStringValue(XSSFRow row, int columnIndex) {
        XSSFCell cell = row.getCell(columnIndex);
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                cell.setCellType(CellType.STRING);
            }
            return cell.getStringCellValue();
        }
        return null;
    }
}
