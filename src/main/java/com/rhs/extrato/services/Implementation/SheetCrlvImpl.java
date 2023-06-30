package com.rhs.extrato.services.Implementation;

import com.rhs.extrato.models.FaturaCrlv;
import com.rhs.extrato.repositories.FaturaCrlvRepository;
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
public class SheetCrlvImpl implements SheetService {
    @Autowired
    FaturaCrlvRepository faturaCrlvRepository;

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
                .orElse(-1) + 2;


        try {
            if (startRow != -1) {
                Set<FaturaCrlv> dadosSet = IntStream.range(startRow, sheet.getLastRowNum())
                        .mapToObj(sheet::getRow)
                        .filter(Objects::nonNull)
                        .map(row -> {
                            FaturaCrlv dados = FaturaCrlv.builder()
                                    .id(UUID.randomUUID())
                                    .passagem(getStringValue(row, 0))
                                    .consulta(getStringValue(row, 1))
                                    .usuario(getStringValue(row, 2))
                                    .dataHora(TimeUtils.convertToDateTime(Double.parseDouble(getStringValue(row, 3))))
                                    .cliente(getStringValue(row, 4))
                                    .custo(getStringValue(row, 5))
                                    .unidade(getStringValue(row, 6))
                                    .documento(getStringValue(row, 8))
                                    .build();

                            return dados;
                        })
                        .collect(Collectors.toSet());

                faturaCrlvRepository.saveAll(dadosSet);
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
