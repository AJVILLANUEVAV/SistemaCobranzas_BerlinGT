package com.cobranzas.web.dto;


import java.util.List;

import lombok.Data;

@Data
public class DeudoresDto {

	private Long id;

	private String nombres;

	private String apellidos;

	private String correo;

	private String telefono;

	private String direccion;

	private String provincia;

	private List<RegistroDeudaDto> registro_deuda;

}
