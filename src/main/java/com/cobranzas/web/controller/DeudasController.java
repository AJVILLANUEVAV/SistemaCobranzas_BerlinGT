package com.cobranzas.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.dto.RegistroDeudaDto;
import com.cobranzas.web.service.impl.DeudoresService;
import com.cobranzas.web.service.impl.RegistroDeudaService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class DeudasController {

	@Autowired
	private DeudoresService deudoresService;
	@Autowired
	private RegistroDeudaService registroDeudaService;

	public DeudasController(DeudoresService deudoresService, RegistroDeudaService registroDeudaService) {
		this.deudoresService = deudoresService;
		this.registroDeudaService=registroDeudaService;
	}
	
	@GetMapping("/RegistrarDeuda")
	public String listRegistrarDeuda(Model model) {
		List<DeudoresDto> deudoresModelo = deudoresService.getAllDeudores();
		
		model.addAttribute("deudoresModelo", deudoresModelo);
		
		return "Registrar_Deuda";
	}
	
	@GetMapping("/ListarTotalDeuda")
	public String listDeudaTotal(Model model) {
		List<RegistroDeudaDto> registroDeudaTotalModelo = registroDeudaService.getAllRegistroDeuda();
		
		model.addAttribute("registroDeudaTotalModelo", registroDeudaTotalModelo);
		
		return "Listar_DeudasTotales";
	}
	
	@GetMapping("/RegistrarDeuda/new")
	public String createRegistrarDeuda(Model model) {

		RegistroDeudaDto registroDeuda = new RegistroDeudaDto();
		
		model.addAttribute("RegistrarDeuda", registroDeuda);

		return "Crear_RegistrarDeuda";
	}
	
	@GetMapping("/RegistrarDeuda/nombrescompletos")
	public String nombreRegistrarDeuda(@RequestParam("id") Long id, Model model) {

		RegistroDeudaDto registroDeuda = registroDeudaService.registrarDeudaNuevo(id);
		
		model.addAttribute("RegistrarDeuda", registroDeuda);

		return "Crear_RegistrarDeuda";
	}
	
	@PostMapping("/RegistrarDeuda")
	public String saveRegistrarDeuda(@ModelAttribute("RegistrarDeuda") RegistroDeudaDto registroDeuda) {
	    
	    registroDeudaService.saveRegistroDeuda(registroDeuda);
	    return "redirect:/RegistrarDeuda";
	}
	
	@GetMapping("/listaDeudasTotales/name")
	public String findDeudasTotalesByName(@RequestParam(name = "buscarNombre_Apellido", required = false) String buscarNombre_Apellido, Model model) {
		List<DeudoresDto> lista = deudoresService.getDeudoresByName("%".concat(buscarNombre_Apellido).concat("%"));
		model.addAttribute("deudoresModelo", lista);
		return "Registrar_Deuda";
	}
		
}
