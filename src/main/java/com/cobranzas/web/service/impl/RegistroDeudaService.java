package com.cobranzas.web.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.dto.RegistroDeudaDto;
import com.cobranzas.web.entity.Deudores;
import com.cobranzas.web.entity.RegistroDeuda;
import com.cobranzas.web.entity.RegistroMovimientos;
import com.cobranzas.web.repository.IDeudoresRepository;
import com.cobranzas.web.repository.IRegistroDeudaRepository;
import com.cobranzas.web.repository.IRegistroMovimientosRepository;
import com.cobranzas.web.service.IRegistroDeudaService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RegistroDeudaService implements IRegistroDeudaService {

	private IRegistroDeudaRepository registroDeudaRepository;
	private IDeudoresRepository deudoresRepository;
	private IRegistroMovimientosRepository registroMovimientosRepository;
	private DeudoresService deudoresService;

	public RegistroDeudaService(IRegistroDeudaRepository registroDeudaRepository,
			IDeudoresRepository deudoresRepository, IRegistroMovimientosRepository registroMovimientosRepository, DeudoresService deudoresService) {
		this.registroDeudaRepository = registroDeudaRepository;
		this.deudoresRepository = deudoresRepository;
		this.registroMovimientosRepository = registroMovimientosRepository;
		this.deudoresService=deudoresService;
	}

	@Override
	public List<RegistroDeudaDto> getAllRegistroDeuda() {
		List<RegistroDeuda> list = registroDeudaRepository.findAll();

		List<RegistroDeudaDto> listDto = new ArrayList<>();

		for (RegistroDeuda registroDeuda : list) {
			RegistroDeudaDto dto = new RegistroDeudaDto();
			dto.setId(registroDeuda.getId());
			dto.setNombreDeudor(
					registroDeuda.getDeudores().getNombres() + " " + registroDeuda.getDeudores().getApellidos());
			
			dto.setFechaDeuda(ParseDateToString(registroDeuda.getFechaDeuda()));
			dto.setTotalDeuda(registroDeuda.getTotalDeuda());
			dto.setAdelanto(registroDeuda.getAdelanto());
			dto.setSaldoDeuda(registroDeuda.getSaldoDeuda());
			dto.setDocumentoTransferencia(registroDeuda.getDocumentoTransferencia());
			dto.setTipoPago(registroDeuda.getTipoPago());
			dto.setNumeroBoleta(registroDeuda.getNumeroBoleta());
			dto.setNumeroSerie(registroDeuda.getNumeroSerie());
			dto.setDeudoresId(registroDeuda.getDeudoresId());

//			log.info("No copia Deudoresid: {}", registroDeuda.getDeudoresId());
			System.out.println("No copia Deudoresid: {}");

			listDto.add(dto);
		}
		return listDto;
	}

	

	@Override
	public List<RegistroDeudaDto> getRegistroDeudaByName(String name) {
		List<RegistroDeuda> list = registroDeudaRepository.findByNameContaining(name);
		List<RegistroDeudaDto> listDto = new ArrayList<>();

		for (RegistroDeuda registroDeuda : list) {
			RegistroDeudaDto dto = new RegistroDeudaDto();
			dto.setId(registroDeuda.getId());
			dto.setNombreDeudor(
					registroDeuda.getDeudores().getNombres() + " " + registroDeuda.getDeudores().getApellidos());
			// dto.setFechaDeuda(registroDeuda.getFechaDeuda());
			dto.setTotalDeuda(registroDeuda.getTotalDeuda());
			dto.setAdelanto(registroDeuda.getAdelanto());
			dto.setSaldoDeuda(registroDeuda.getSaldoDeuda());
			dto.setDocumentoTransferencia(registroDeuda.getDocumentoTransferencia());
			dto.setTipoPago(registroDeuda.getTipoPago());
			dto.setNumeroBoleta(registroDeuda.getNumeroBoleta());
			dto.setNumeroSerie(registroDeuda.getNumeroSerie());
			dto.setDeudoresId(registroDeuda.getDeudoresId());

			listDto.add(dto);
		}

		return listDto;
	}

	@Override
	public RegistroDeudaDto saveRegistroDeuda(RegistroDeudaDto dto) {
		RegistroDeuda registroDeuda = new RegistroDeuda();

		Deudores deudores = deudoresRepository.findById(dto.getDeudoresId()).get();
		registroDeuda.setDeudores(deudores);

		registroDeuda.setDocumentoTransferencia(dto.getDocumentoTransferencia());

		registroDeuda.setFechaDeuda(parseFechaDto(dto.getFechaDeuda()));
		registroDeuda.setNumeroBoleta(dto.getNumeroBoleta());
		registroDeuda.setNumeroSerie(dto.getNumeroSerie());
		registroDeuda.setTotalDeuda(dto.getTotalDeuda());
		registroDeuda.setAdelanto(dto.getAdelanto());
		registroDeuda.setSaldoDeuda(dto.getSaldoDeuda());
		registroDeuda.setTipoPago(dto.getTipoPago());

		registroDeudaRepository.save(registroDeuda);

		RegistroMovimientos rMovimientos = new RegistroMovimientos();
		rMovimientos.setDeudores(deudores);

		rMovimientos.setPago(dto.getTotalDeuda());
		rMovimientos.setAdelanto(dto.getAdelanto());
		rMovimientos.setSaldo(dto.getSaldoDeuda());
		BigDecimal saldoTotalAcumulado = registroMovimientosRepository.obteniendoSaldoTotalByDeudorId(deudores.getId());
		if (saldoTotalAcumulado == null || saldoTotalAcumulado.intValue() <= 0) {
			saldoTotalAcumulado = BigDecimal.ZERO;
			dto.setMensaje("La deuda no puede ser negativo o cero");
			return dto;
		}
		log.info("El saldoTotalAcumulado" + ": " + saldoTotalAcumulado);
		rMovimientos.setSaldoTotal(rMovimientos.getSaldo().add(saldoTotalAcumulado));
		rMovimientos.setFechaRegistro(parseFechaDto(dto.getFechaDeuda()));
		rMovimientos.setResumen(dto.getNumeroBoleta() + " - " + dto.getNumeroSerie());
		rMovimientos.setTipoMovimiento("Transaccion: DEUDA");
		log.info("Se registro correctamente");
		registroMovimientosRepository.save(rMovimientos);

		return dto;
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

	@Override
	public RegistroDeuda getRegistroDeudaByID(Long id) {
		return registroDeudaRepository.findById(id).get();
	}

	@Override
	public RegistroDeuda updateRegistroDeuda(RegistroDeuda registroDeuda) {
		return registroDeudaRepository.save(registroDeuda);
	}

	@Override
	public void deleteRegistroDeudaByID(Long id) {
		registroDeudaRepository.deleteById(id);
	}

	public RegistroDeudaDto registrarDeudaNuevo(Long id) {

		RegistroDeudaDto registroDeuda = new RegistroDeudaDto();
		
		log.info("id: {}",id);
		
		DeudoresDto deudores = deudoresService.getDeudoresByID(id);

		registroDeuda.setNombreDeudor(deudores.getNombres()+" "+deudores.getApellidos());

		registroDeuda.setDeudoresId(deudores.getId());
		
		registroDeuda.setFechaDeuda(getFechaActualString());
		
		return registroDeuda;
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
