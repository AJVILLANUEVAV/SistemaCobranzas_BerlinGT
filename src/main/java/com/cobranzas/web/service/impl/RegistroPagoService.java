package com.cobranzas.web.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.dto.RegistroDeudaDto;
import com.cobranzas.web.dto.RegistroPagoDto;
import com.cobranzas.web.entity.Deudores;
import com.cobranzas.web.entity.RegistroDeuda;
import com.cobranzas.web.entity.RegistroMovimientos;
import com.cobranzas.web.entity.RegistroPago;
import com.cobranzas.web.repository.IDeudoresRepository;
import com.cobranzas.web.repository.IRegistroMovimientosRepository;
import com.cobranzas.web.repository.IRegistroPagoRepository;
import com.cobranzas.web.service.IRegistroPagoService;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class RegistroPagoService implements IRegistroPagoService{

	private IRegistroPagoRepository registroPagoRepository;
	private IDeudoresRepository deudoresRepository;
	private IRegistroMovimientosRepository registroMovimientosRepository;
	private DeudoresService deudoresService;
	
	public RegistroPagoService(IRegistroPagoRepository registroPagoRepository,IDeudoresRepository deudoresRepository, IRegistroMovimientosRepository registroMovimientosRepository, DeudoresService deudoresService) {
		this.registroPagoRepository=registroPagoRepository;
		this.deudoresRepository=deudoresRepository;
		this.registroMovimientosRepository=registroMovimientosRepository;
		this.deudoresService=deudoresService;
	}
	
	@Override
	public List<RegistroPagoDto> getAllRegistroPago() {		
//		return registroPagoRepository.findAll();
		List<RegistroPago> list = registroPagoRepository.findAll();
		
		List<RegistroPagoDto> listDto = new ArrayList<>();
		
		for (RegistroPago regPago : list) {
			RegistroPagoDto dto = new RegistroPagoDto();
			dto.setId(regPago.getId_deudor());
			dto.setNombreDeudor(regPago.getDeudores().getNombres()+" "+regPago.getDeudores().getApellidos());
			dto.setFechaPago(ParseDateToString(regPago.getFechaPago()));
			dto.setBoleta(regPago.getBoleta());
			dto.setSerie(regPago.getSerie());
			dto.setDocumentoTransferencia(regPago.getDocumentoTransferencia());
			dto.setMontoPago(regPago.getMontoPago());
			dto.setTipoPago(regPago.getTipoPago());
			
			
			listDto.add(dto);
		}
		
		return listDto;
	}
	
	@Override
	public List<RegistroPagoDto> getRegistroPagoByName(String name) {
		List<RegistroPago> list = registroPagoRepository.findByNameContaining(name);
		List<RegistroPagoDto> listDto = new ArrayList<>();

		for (RegistroPago registroPago : list) {
			RegistroPagoDto dto = new RegistroPagoDto();
			dto.setNombreDeudor(registroPago.getDeudores().getNombres()+" "+registroPago.getDeudores().getApellidos());
			dto.setFechaPago(ParseDateToString(registroPago.getFechaPago()));
			dto.setBoleta(registroPago.getBoleta());
			dto.setSerie(registroPago.getSerie());
			dto.setMontoPago(registroPago.getMontoPago());
			dto.setTipoPago(registroPago.getTipoPago());
			dto.setDocumentoTransferencia(registroPago.getDocumentoTransferencia());
			
			listDto.add(dto);
		}

		return listDto;
	}

	@Override
	public RegistroPagoDto saveRegistroPago(RegistroPagoDto dto) {
		RegistroPago registroPago=new RegistroPago();
		
		Deudores deudores=deudoresRepository.findById(dto.getDeudoresId()).get();
		registroPago.setDeudores(deudores);
		
		BigDecimal saldoTotalAcumulado=registroMovimientosRepository.obteniendoSaldoTotalByDeudorId(deudores.getId());
		if (saldoTotalAcumulado == null || saldoTotalAcumulado.intValue() == 0) {
		    saldoTotalAcumulado=BigDecimal.ZERO;
		    dto.setMensaje("El deudor no tiene deudas pendientes");
		    return dto;
		}
		
		registroPago.setFechaPago(parseFechaDto(dto.getFechaPago()));
		registroPago.setBoleta(dto.getBoleta());
		registroPago.setSerie(dto.getSerie());
		registroPago.setMontoPago(dto.getMontoPago());
		registroPago.setDocumentoTransferencia(dto.getDocumentoTransferencia());
		registroPago.setTipoPago(dto.getTipoPago());
		
		registroPagoRepository.save(registroPago);
		
		RegistroMovimientos rMovimientos=new RegistroMovimientos();
		rMovimientos.setDeudores(deudores);
		
		rMovimientos.setPago(dto.getMontoPago());
		rMovimientos.setAdelanto(BigDecimal.ZERO);
		rMovimientos.setSaldo(BigDecimal.ZERO);
		
		rMovimientos.setSaldoTotal(saldoTotalAcumulado.subtract(rMovimientos.getPago()));
		log.info("El saldoTotalAcumulado"+": "+saldoTotalAcumulado);
		rMovimientos.setFechaRegistro(parseFechaDto(dto.getFechaPago()));
		rMovimientos.setResumen(dto.getBoleta()+" - "+dto.getSerie());
		rMovimientos.setTipoMovimiento("Transaccion: PAGO");
		log.info("Se registro correctamente");
		registroMovimientosRepository.save(rMovimientos);
		
		
		return dto;
	}

	@Override
	public RegistroPagoDto getRegistroPagoByID(Long id) {
		RegistroPago registroPago = registroPagoRepository.findById(id).get();
		
		RegistroPagoDto dto=new RegistroPagoDto();
		dto.setId(registroPago.getId_deudor());
		dto.setNombreDeudor(registroPago.getDeudores().getNombres()+" "+registroPago.getDeudores().getApellidos());
		dto.setBoleta(registroPago.getBoleta());
		dto.setSerie(registroPago.getSerie());
		dto.setDocumentoTransferencia(registroPago.getDocumentoTransferencia());
		dto.setFechaPago(ParseDateToString(registroPago.getFechaPago()));
		dto.setMontoPago(registroPago.getMontoPago());
		dto.setTipoPago(registroPago.getTipoPago());
		
		return dto;
	}

	@Override
	public RegistroPagoDto updateRegistroPago(RegistroPagoDto dto) {
		RegistroPago registroPago = registroPagoRepository.findById(dto.getId()).get();
		registroPago.setId_deudor(dto.getId());
		registroPago.setFechaPago(parseFechaDto(dto.getFechaPago()));
		registroPago.setBoleta(dto.getBoleta());
		registroPago.setSerie(dto.getSerie());
		registroPago.setMontoPago(dto.getMontoPago());
		registroPago.setDocumentoTransferencia(dto.getDocumentoTransferencia());
		registroPago.setTipoPago(dto.getTipoPago());
		
		registroPagoRepository.save(registroPago);
		return dto;
	}
	@Override
	public void deleteRegistroPagoByID(Long id) {
		registroPagoRepository.deleteById(id);
	}

	private Date parseFechaDto(String fechaDeuda) {
		// Convertir el String a LocalDate
		LocalDate fechaLocal = LocalDate.parse(fechaDeuda, DateTimeFormatter.ISO_LOCAL_DATE);
		Date fechaSql = Date.valueOf(fechaLocal);
		return fechaSql;
	}
	
	private String ParseDateToString(Date fechaSql) {
		LocalDate fechaLocal = fechaSql.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = fechaLocal.format(formatter);
		return fechaStr;
	}

	public RegistroPagoDto registrarPagoNuevo(Long idDeudor) {
		RegistroPagoDto registroPagoDTO = new RegistroPagoDto();
		
		DeudoresDto deudores = deudoresService.getDeudoresByID(idDeudor);

		registroPagoDTO.setNombreDeudor(deudores.getNombres() + " " + deudores.getApellidos());
		registroPagoDTO.setDeudoresId(deudores.getId());
		registroPagoDTO.setFechaPago(getFechaActualString());
		return registroPagoDTO;
	}
	
	private String getFechaActualString() {
		// Get the current date
        LocalDate currentDate = LocalDate.now();
        // Create a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Format the current date
        return currentDate.format(formatter);
		
	}

}
