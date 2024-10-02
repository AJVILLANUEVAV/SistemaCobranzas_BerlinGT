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
import com.cobranzas.web.dto.RegistroPagoDto;
import com.cobranzas.web.entity.Deudores;
import com.cobranzas.web.entity.RegistroDeuda;
import com.cobranzas.web.entity.RegistroPago;
import com.cobranzas.web.service.impl.DeudoresService;
import com.cobranzas.web.service.impl.RegistroDeudaService;
import com.cobranzas.web.service.impl.RegistroPagoService;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class PagosController {

	@Autowired
	private DeudoresService deudoresService;
	@Autowired
	private RegistroPagoService registroPagoService;

	public PagosController(DeudoresService deudoresService, RegistroPagoService registroPagoService) {
		this.deudoresService = deudoresService;
		this.registroPagoService = registroPagoService;
	}

	@GetMapping("/RegistrarPagos")
	public String listRegistrarPagos(Model model) {
		List<DeudoresDto> deudoresModelo = deudoresService.getAllDeudores();

		model.addAttribute("deudoresModelo", deudoresModelo);

		return "Registrar_Pago";
	}

	@PostMapping("/RegistrarPagos")
	public String saveRegistrarPago(@ModelAttribute("RegistrarPagos") RegistroPagoDto registroPago, Model model) {
		
		RegistroPagoDto dto = registroPagoService.saveRegistroPago(registroPago);
		log.info("Entre a este metodo");
		if (dto.getMensaje() != null) {
			model.addAttribute("error", dto.getMensaje());
			return "Crear_RegistrarPagos";
		}

		return "redirect:/RegistrarPagos";

	}

	@GetMapping("/RegistrarPagos/new")
	public String createRegistrarPagos(Model model) {

		RegistroPagoDto registroPago = new RegistroPagoDto();

		model.addAttribute("RegistrarPago", registroPago);

		return "Crear_RegistrarPagos";
	}

	@GetMapping("/RegistrarPagos/nombrescompletos")
	public String nombreRegistrarDeuda(@RequestParam("id") Long idDeudor, Model model) {

		RegistroPagoDto registroPago = registroPagoService.registrarPagoNuevo(idDeudor);

		model.addAttribute("RegistrarPagos", registroPago);

		return "Crear_RegistrarPagos";
	}

	@GetMapping("/Listar_PagosTotales")
	public String listPagoTotal(Model model) {
		List<RegistroPagoDto> registroPagoTotalModelo = registroPagoService.getAllRegistroPago();

		model.addAttribute("registroPagoTotalModelo", registroPagoTotalModelo);

		return "Listar_PagosTotales";
	}

	@GetMapping("/listaPagosTotales/name")
	public String findPagosTotalesByName(
			@RequestParam(name = "buscarNombre_Apellido", required = false) String buscarNombre_Apellido, Model model) {

		List<RegistroPagoDto> registroPagoTotalModelo = registroPagoService
				.getRegistroPagoByName(buscarNombre_Apellido);
		model.addAttribute("registroPagoTotalModelo", registroPagoTotalModelo);
		return "Listar_PagosTotales";
	}
}
