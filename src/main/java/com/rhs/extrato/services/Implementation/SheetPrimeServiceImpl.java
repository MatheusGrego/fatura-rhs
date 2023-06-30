package com.rhs.extrato.services.Implementation;

import com.rhs.extrato.models.Fatura;
import com.rhs.extrato.repositories.FaturaRepository;
import com.rhs.extrato.services.SheetService;
import com.rhs.extrato.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SheetPrimeServiceImpl implements SheetService {
    @Autowired
    FaturaRepository faturaRepository;

    @Override
    public boolean validateSheet(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new EmptyFileException();
        String mimeType = new Tika().detect(file.getBytes());
        if (!mimeType.equals("application/x-tika-ooxml"))
            throw new MultipartException("Tipo de arquivo não permitido");
        return true;
    }

    @Override
    public void insertSheet(MultipartFile file) throws IOException {
        if (!validateSheet(file)) {
            throw new IOException();
        }

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);

        int startRow = IntStream.range(0, sheet.getLastRowNum())
                .filter(i -> {
                    XSSFRow row = sheet.getRow(i);
                    return row != null && row.getPhysicalNumberOfCells() >= 7;

                })
                .findFirst()
                .orElse(-1);


        try {
            if (startRow != -1) {
                Set<Fatura> dadosList = IntStream.range(startRow, sheet.getLastRowNum())
                        .mapToObj(sheet::getRow)
                        .filter(Objects::nonNull)
                        .map(row -> {
                            Fatura dados = Fatura.builder()
                                    .id(UUID.randomUUID())
                                    .documento(getStringValue(row, 1))
                                    .login(getStringValue(row, 2))
                                    .dataConsulta(TimeUtils.convertExcelDate(getStringValue(row, 3)))
                                    .horaConsulta(TimeUtils.convertHours(getStringValue(row, 4)))
                                    .valor(getStringValue(row, 5))
                                    .ipConsulta(getStringValue(row, 6))
                                    .nomeConsulta(getStringValue(row, 7))
                                    .build();

                            return dados;
                        })
                                .collect(Collectors.toSet());

                faturaRepository.saveAll(dadosList);
            }
        } catch (NumberFormatException e) {
            log.info("Error: " + e.getMessage());
            throw e;
        }
        workbook.close();


    }

    @Override
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
