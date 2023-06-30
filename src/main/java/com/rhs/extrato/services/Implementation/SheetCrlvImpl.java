package com.rhs.extrato.services.Implementation;

import com.rhs.extrato.models.FaturaCrlv;
import com.rhs.extrato.repositories.FaturaCrlvRepository;
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
public class SheetCrlvImpl implements SheetService {
    @Autowired
    FaturaCrlvRepository faturaCrlvRepository;

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
                .orElse(-1) + 2;


        try {
            if (startRow != -1) {
                Set<FaturaCrlv> dadosSet = IntStream.range(startRow, sheet.getLastRowNum())
                        .mapToObj(sheet::getRow)
                        .filter(Objects::nonNull)
                        .map(row -> {
                            FaturaCrlv dados = FaturaCrlv.builder()
                                    .id(UUID.randomUUID())
                                    .passagem(FileUtils.getStringValue(row, 0))
                                    .consulta(FileUtils.getStringValue(row, 1))
                                    .usuario(FileUtils.getStringValue(row, 2))
                                    .dataHora(TimeUtils.convertToDateTime(Double.parseDouble(FileUtils.getStringValue(row, 3))))
                                    .cliente(FileUtils.getStringValue(row, 4))
                                    .custo(FileUtils.getStringValue(row, 5))
                                    .unidade(FileUtils.getStringValue(row, 6))
                                    .documento(FileUtils.getStringValue(row, 8))
                                    .build();

                            return dados;
                        })
                        .collect(Collectors.toSet());

                faturaCrlvRepository.saveAll(dadosSet);
            }
        } catch (NumberFormatException e) {
            log.error("Error: " + e.getMessage());
            throw e;
        }finally {
            workbook.close();
        }



    }

}
