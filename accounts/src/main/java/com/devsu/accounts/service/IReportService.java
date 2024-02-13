package com.devsu.accounts.service;

import com.devsu.accounts.dto.report.Report;

import java.time.LocalDate;
import java.util.List;

public interface IReportService {
    Report generateReport(LocalDate dateStart, LocalDate dateEnd, Long clientId);
}
