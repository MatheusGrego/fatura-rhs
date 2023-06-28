package com.rhs.extrato.services.Implementation;

import com.rhs.extrato.services.SheetService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SheetCrlvImpl implements SheetService {
    @Override
    public void insertSheet(MultipartFile file) throws IOException {

    }

    @Override
    public boolean validateSheet(MultipartFile file) throws IOException {
        return false;
    }

    @Override
    public String getStringValue(XSSFRow row, int columnIndex) {
        return null;
    }
}
