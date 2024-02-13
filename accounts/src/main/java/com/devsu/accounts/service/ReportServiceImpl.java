package com.devsu.accounts.service;

import com.devsu.accounts.dto.report.AccountDetail;
import com.devsu.accounts.dto.report.MovementDetail;
import com.devsu.accounts.dto.report.Report;
import com.devsu.accounts.entities.Cliente;
import com.devsu.accounts.entities.Cuenta;
import com.devsu.accounts.entities.Movimiento;
import com.devsu.accounts.repository.IClientRepository;
import com.devsu.accounts.repository.IMovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements IReportService{

    private final IMovementRepository movementRepository;
    private final IClientRepository  clientRepository;


    public ReportServiceImpl(IMovementRepository movementRepository, IClientRepository clientRepository) {
        this.movementRepository = movementRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Report generateReport(LocalDate dateStart, LocalDate dateEnd, Long clientId) {

        //client exist
        Cliente cliente = clientRepository.findById(clientId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %s no existe", clientId))
        );

        List<Movimiento> data = movementRepository.findMovementsByClienteAndDateRange(clientId, dateStart.atStartOfDay(), dateEnd.plusDays(1).atStartOfDay());
        Report report = new Report();
        report.setFecha(dateStart);
        report.setFechaFin(dateEnd);
        report.setCliente(cliente.getNombre());
        Map<Long, AccountDetail> accountDetailsMap = new HashMap<>();

        for (Movimiento movimiento : data) {
            //set account
            Cuenta cuenta = movimiento.getCuenta();

            AccountDetail accountDetail = accountDetailsMap.computeIfAbsent(cuenta.getId(), k -> new AccountDetail());
            accountDetail.setCuenta(cuenta.getNumeroCuenta());
            accountDetail.setSaldoInicial(cuenta.getSaldoInicial());
            accountDetail.setTipoCuenta(cuenta.getTipoCuenta());
            accountDetail.setEstado(cuenta.getEstado());

            if (accountDetail.getMovimientos() == null) {
                accountDetail.setMovimientos(new HashSet<>());
            }

            MovementDetail movementDetail = new MovementDetail();
            movementDetail.setFecha(movimiento.getFecha());
            movementDetail.setSaldo(movimiento.getSaldo());
            movementDetail.setMovimiento(movimiento.getTipoMovimiento());
            movementDetail.setMonto(movimiento.getValor());

            accountDetail.getMovimientos().add(movementDetail);



        }



        report.setCuentas(new HashSet<>(accountDetailsMap.values()));

        return report;
    }
}
