package com.cobranzas.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cobranzas.web.dto.DeudoresDto;
import com.cobranzas.web.entity.Deudores;
import com.cobranzas.web.service.impl.DeudoresService;

import lombok.extern.log4j.Log4j2;


@Controller
@Log4j2
public class DeudoresController {

	@Autowired
	private DeudoresService deudoresService;
	

	public DeudoresController(DeudoresService deudoresService) {
		this.deudoresService = deudoresService;		
	}

	@GetMapping("/deudores")
	public String listDeudores(Model model) {
		List<DeudoresDto> deudoresModelo = deudoresService.getAllDeudores();
		log.info("Controller ::::: deudores {}",deudoresModelo);
		model.addAttribute("deudoresModelo", deudoresModelo);
		
		return "Registrar_Listar_Deudores";
	}
	
	
	
	@GetMapping("/deudores/edit/{id}")
	public String editDeudoresForm(@PathVariable Long id, Model model) {
		DeudoresDto st = deudoresService.getDeudoresByID(id);

		model.addAttribute("deudores", st);

		return "Actualizar_deudores";
	}
	
	@PostMapping("/deudores/{id}")
	public String actualizarDeudor(@PathVariable Long id, @ModelAttribute("deudores") Deudores deudores) {
        
		//Sacar el esudiante de la b.d. por el id
		DeudoresDto existentDeudor = deudoresService.getDeudoresByID(id);
        // Actualizando los datos
		existentDeudor.setId(id);
		existentDeudor.setNombres(deudores.getNombres());
		existentDeudor.setApellidos(deudores.getApellidos());
		existentDeudor.setCorreo(deudores.getCorreo());
		existentDeudor.setTelefono(deudores.getTelefono());
		existentDeudor.setDireccion(deudores.getDireccion());
		existentDeudor.setProvincia(deudores.getProvincia());

        //Guardar el estudiante actualizado
        deudoresService.updateDeudores(existentDeudor);
        
        return "redirect:/deudores";
    }
	
	@GetMapping("/deudores/new")
	public String createDeudoresForm(Model model) {

		Deudores deudores = new Deudores();

		model.addAttribute("deudores", deudores);

		return "Crear_Deudores";
	}
	
	
	@PostMapping("/deudores")
	public String saveDeudor(@ModelAttribute("deudores") DeudoresDto deudores) {
		deudoresService.saveDeudores(deudores);
		return "redirect:/deudores";
	}
	
	
	@GetMapping("/deudores/delete/{id}")
    public String deleteDeudor(@PathVariable Long id) {
		deudoresService.deleteDeudoresByID(id);
        return "redirect:/deudores";
    }
		
	@GetMapping("/deudores/name")
	public String findDeudorByName(@RequestParam(name = "buscarNombre_Apellido", required = false) String buscarNombre_Apellido, Model model) {
		List<DeudoresDto> lista = deudoresService.getDeudoresByName("%".concat(buscarNombre_Apellido).concat("%"));
		model.addAttribute("deudoresModelo", lista);
		return "Registrar_Listar_Deudores";
	}
	
	
}
