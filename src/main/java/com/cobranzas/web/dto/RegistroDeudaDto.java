package com.cobranzas.web.dto;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class RegistroDeudaDto extends MensajesDto{

    private Long id;
    
    private String nombreDeudor;
    
    //@DateTimeFormat(pattern = "dd-MM-yyyy")
    private String fechaDeuda;
    
    private String numeroBoleta;  
    
    private String numeroSerie;
    
    private BigDecimal totalDeuda;
    
    private BigDecimal adelanto;
    
    private BigDecimal saldoDeuda;
    
    private String tipoPago;
    
    private String documentoTransferencia;
    
    private Long deudoresId;
}
