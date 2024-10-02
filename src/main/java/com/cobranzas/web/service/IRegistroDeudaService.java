package com.cobranzas.web.service;

import java.util.List;

import com.cobranzas.web.dto.RegistroDeudaDto;
import com.cobranzas.web.entity.RegistroDeuda;

public interface IRegistroDeudaService {

	List<RegistroDeudaDto> getAllRegistroDeuda();
	List<RegistroDeudaDto> getRegistroDeudaByName(String name);
	RegistroDeudaDto saveRegistroDeuda(RegistroDeudaDto registroDeuda);
	RegistroDeuda getRegistroDeudaByID(Long id);
	RegistroDeuda updateRegistroDeuda(RegistroDeuda registroDeuda);
	void deleteRegistroDeudaByID(Long id);
}
