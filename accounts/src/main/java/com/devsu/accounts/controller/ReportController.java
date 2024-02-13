package com.devsu.accounts.controller;

import com.devsu.accounts.dto.report.Report;
import com.devsu.accounts.service.IReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    private final IReportService service;

    public ReportController(IReportService service) {
        this.service = service;
    }


    @GetMapping
    public Report generateReport(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                               @RequestParam("clienteId") Long clientId) {

       return service.generateReport(fechaInicio, fechaFin, clientId);

    }
}
