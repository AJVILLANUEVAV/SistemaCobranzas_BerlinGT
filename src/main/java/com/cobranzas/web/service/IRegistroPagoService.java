package com.cobranzas.web.service;

import java.util.List;

import com.cobranzas.web.dto.RegistroPagoDto;

public interface IRegistroPagoService {

	List<RegistroPagoDto> getAllRegistroPago();
	List<RegistroPagoDto> getRegistroPagoByName(String name);
	RegistroPagoDto saveRegistroPago(RegistroPagoDto registroPago);
	RegistroPagoDto getRegistroPagoByID(Long id);
	RegistroPagoDto updateRegistroPago(RegistroPagoDto registroPago);
	void deleteRegistroPagoByID(Long id);
}
