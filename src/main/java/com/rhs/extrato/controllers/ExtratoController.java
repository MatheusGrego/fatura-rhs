package com.rhs.extrato.controllers;

import com.rhs.extrato.services.ExtratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {
    @Autowired
    ExtratoService extratoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadExcel(@RequestPart("arquivo")MultipartFile file){
        extratoService.readFile(file);
        return ResponseEntity.status(201).body("Dados salvos!");

    }


}
