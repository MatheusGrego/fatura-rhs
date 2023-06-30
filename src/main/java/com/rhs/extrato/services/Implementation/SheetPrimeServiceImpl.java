package com.rhs.extrato.services.Implementation;

import com.rhs.extrato.models.Fatura;
import com.rhs.extrato.repositories.FaturaRepository;
import com.rhs.extrato.services.SheetService;
import com.rhs.extrato.utils.FileUtils;
import com.rhs.extrato.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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
    public void insertSheet(InputStream file) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(file);
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
                                    .documento(FileUtils.getStringValue(row, 1))
                                    .login(FileUtils.getStringValue(row, 2))
                                    .dataConsulta(TimeUtils.convertExcelDate(FileUtils.getStringValue(row, 3)))
                                    .horaConsulta(TimeUtils.convertHours(FileUtils.getStringValue(row, 4)))
                                    .valor(FileUtils.getStringValue(row, 5))
                                    .ipConsulta(FileUtils.getStringValue(row, 6))
                                    .nomeConsulta(FileUtils.getStringValue(row, 7))
                                    .build();

                            return dados;
                        })
                        .collect(Collectors.toSet());

                faturaRepository.saveAll(dadosList);
            }
        } catch (NumberFormatException e) {
            log.error("Error: " + e.getMessage());
            throw e;
        } finally {
            workbook.close();
        }
    }


}
