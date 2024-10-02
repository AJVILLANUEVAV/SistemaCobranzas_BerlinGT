package com.cobranzas.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.entity.Deudores;
import com.cobranzas.web.repository.IDeudoresRepository;
import com.cobranzas.web.service.IDeudoresService;

@Service
public class DeudoresService implements IDeudoresService {

	private IDeudoresRepository deudoresRepository;

	public DeudoresService(IDeudoresRepository deudoresRepository) {
		this.deudoresRepository = deudoresRepository;
	}

	@Override
	public List<DeudoresDto> getAllDeudores() {
		List<Deudores> list = deudoresRepository.findAll();
		
		List<DeudoresDto> listDto = new ArrayList<>();
		
		for (Deudores deudores : list) {
			DeudoresDto dto = new DeudoresDto();
			dto.setId(deudores.getId());
			dto.setNombres(deudores.getNombres());
			dto.setApellidos(deudores.getApellidos());
			dto.setDireccion(deudores.getDireccion());
			dto.setCorreo(deudores.getCorreo());
			dto.setProvincia(deudores.getProvincia());
			dto.setTelefono(deudores.getTelefono());
			
			listDto.add(dto);
		}
		
		return listDto;
	}
	
	@Override
	public List<DeudoresDto> getDeudoresByName(String name) {
		List<Deudores> list = deudoresRepository.findByNameContaining(name);
		
		List<DeudoresDto> listDto = new ArrayList<>();
		
		for (Deudores deudores : list) {
			DeudoresDto dto = new DeudoresDto();
			dto.setId(deudores.getId());
			dto.setNombres(deudores.getNombres());
			dto.setApellidos(deudores.getApellidos());
			dto.setDireccion(deudores.getDireccion());
			dto.setCorreo(deudores.getCorreo());
			dto.setProvincia(deudores.getProvincia());
			dto.setTelefono(deudores.getTelefono());
			
			listDto.add(dto);
		}
		
		return listDto;
	}

	@Override
	public DeudoresDto saveDeudores(DeudoresDto dto) {

		Deudores deudores =  new  Deudores();
		deudores.setNombres(dto.getNombres());
		deudores.setApellidos(dto.getApellidos());
		deudores.setDireccion(dto.getDireccion());
		deudores.setCorreo(dto.getCorreo());
		deudores.setProvincia(dto.getProvincia());
		deudores.setTelefono(dto.getTelefono());
		
		deudoresRepository.save(deudores);
		return dto;
	}

	@Override
	public DeudoresDto getDeudoresByID(Long id) {
		
		Deudores deudores =  deudoresRepository.findById(id).get();
		
		DeudoresDto dto = new DeudoresDto();
		dto.setId(deudores.getId());
		dto.setNombres(deudores.getNombres());
		dto.setApellidos(deudores.getApellidos());
		dto.setDireccion(deudores.getDireccion());
		dto.setCorreo(deudores.getCorreo());
		dto.setProvincia(deudores.getProvincia());
		dto.setTelefono(deudores.getTelefono());
		
		return dto;
	}

	@Override
	public DeudoresDto updateDeudores(DeudoresDto dto) {

		Deudores deudores =  deudoresRepository.findById(dto.getId()).get();
		deudores.setNombres(dto.getNombres());
		deudores.setApellidos(dto.getApellidos());
		deudores.setDireccion(dto.getDireccion());
		deudores.setCorreo(dto.getCorreo());
		deudores.setProvincia(dto.getProvincia());
		deudores.setTelefono(dto.getTelefono());
		
		deudoresRepository.save(deudores);
		return dto;
	}

	@Override
	public void deleteDeudoresByID(Long id) {
		deudoresRepository.deleteById(id);
	}

}
