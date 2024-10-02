	package com.cobranzas.web.dto;

import java.math.BigDecimal;
import java.sql.Date;


import lombok.Data;

@Data
public class RegistroPagoDto extends MensajesDto{

    private Long id;
    
    private String nombreDeudor;

    private Long deudoresId;    
    
    private String fechaPago;

    private String boleta;

    private String serie;

    private BigDecimal montoPago;

    private String tipoPago;

    private String documentoTransferencia; 
}
