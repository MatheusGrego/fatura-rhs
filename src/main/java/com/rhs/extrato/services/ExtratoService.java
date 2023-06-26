package com.rhs.extrato.services;

import com.rhs.extrato.models.Extrato;
import com.rhs.extrato.repositories.ExtratoRepository;
import com.rhs.extrato.util.DateUtil;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ExtratoService {
    @Autowired
    private ExtratoRepository extratoRepository;


    public void readFile(MultipartFile file) throws IOException{
        if (file.isEmpty()) throw new EmptyFileException();
        String mimeType  = new Tika().detect(file.getBytes());
        if (!mimeType.equals("application/x-tika-ooxml"))
            throw new MultipartException("Tipo de arquivo não permitido");
        fileIterator(file);

    }

    public void fileIterator(MultipartFile file) throws IOException {
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
                List<Extrato> dadosList = IntStream.range(startRow, sheet.getLastRowNum())
                        .mapToObj(sheet::getRow)
                        .filter(Objects::nonNull)
                        .map(row -> {
                            Extrato dados = Extrato.builder()
                                    .id(UUID.randomUUID())
                                    .documento(getStringValue(row, 1))
                                    .login(getStringValue(row, 2))
                                    .dataConsulta(DateUtil.convertExcelDate(getStringValue(row, 3)))
                                    .horaConsulta(getStringValue(row, 4))
                                    .valor(getStringValue(row, 5))
                                    .ipConsulta(getStringValue(row, 6))
                                    .nomeConsulta(getStringValue(row, 7))
                                    .build();

                            return dados;
                        })
                        .toList();

                extratoRepository.saveAll(dadosList);
            }
        } catch (NumberFormatException e) {
            log.info("Planilha inválida!");
            throw e;
        }
        workbook.close();


    }

    private String getStringValue(XSSFRow row, int columnIndex) {
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
