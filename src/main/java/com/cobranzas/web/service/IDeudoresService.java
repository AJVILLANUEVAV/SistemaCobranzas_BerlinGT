package com.cobranzas.web.service;

import java.util.List;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.entity.Deudores;

public interface IDeudoresService {
	
	List<DeudoresDto> getAllDeudores();
	
	List<DeudoresDto> getDeudoresByName(String name);
	DeudoresDto saveDeudores(DeudoresDto deudores);
	DeudoresDto getDeudoresByID(Long id);
	DeudoresDto updateDeudores(DeudoresDto deudores);
	void deleteDeudoresByID(Long id);
}
