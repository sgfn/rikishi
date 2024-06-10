package com.rikishi.rikishi.controller;

import com.itextpdf.text.DocumentException;
import com.rikishi.rikishi.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public void getReport(@RequestParam String saveTo) throws DocumentException, FileNotFoundException {
        reportService.generate(saveTo);
    }
}
