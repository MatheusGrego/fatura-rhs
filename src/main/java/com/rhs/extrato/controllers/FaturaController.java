package com.rhs.extrato.controllers;

import com.rhs.extrato.services.Implementation.SheetCrlvImpl;
import com.rhs.extrato.services.Implementation.SheetPrimeServiceImpl;
import com.rhs.extrato.utils.FileUtils;
import org.apache.poi.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/extrato")
public class FaturaController {
    @Autowired
    SheetPrimeServiceImpl sheetService;
    @Autowired
    SheetCrlvImpl sheetCrlvImpl;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSheet(@RequestPart("arquivo") MultipartFile file) {
        try {
            FileUtils.validateSheet(file);
            sheetService.insertSheet(file.getInputStream());
        } catch (IOException | EmptyFileException | MultipartException | NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Dados salvos! ",HttpStatus.CREATED);
    }

    @PostMapping("/upload/crlv")
    public ResponseEntity<?> uploadSheetCrlv(@RequestPart("arquivo") MultipartFile file) {
        try {
            FileUtils.validateSheet(file);
            sheetCrlvImpl.insertSheet(file.getInputStream());
        } catch (IOException | EmptyFileException | MultipartException | NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>("Dados CRLV salvos",HttpStatus.CREATED);
    }

}
