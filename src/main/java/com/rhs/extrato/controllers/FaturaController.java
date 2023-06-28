package com.rhs.extrato.controllers;

import com.rhs.extrato.services.Implementation.SheetPrimeServiceImpl;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestPart("arquivo") MultipartFile file) {
        try {
            sheetService.insertSheet(file);
        } catch (IOException | EmptyFileException | MultipartException | NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }

}
