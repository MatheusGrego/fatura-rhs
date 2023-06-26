package com.rhs.extrato.controllers;

import com.rhs.extrato.services.ExtratoService;
import org.apache.poi.EmptyFileException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExtratoController {
    @Autowired
    ExtratoService extratoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestPart("arquivo")MultipartFile file){
        try {
            extratoService.readFile(file);
        }catch (IOException | EmptyFileException |MultipartException | NumberFormatException e){
            return ResponseEntity.status(400).body("Error: "+ e.getMessage());
        }
        return ResponseEntity.status(201).body("Dados salvos!");

    }


}
