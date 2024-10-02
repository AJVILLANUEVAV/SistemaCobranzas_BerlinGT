package com.cobranzas.web.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name="registro_deuda")
@Data
public class RegistroDeuda {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="fecha_deuda")
    private Date fechaDeuda;
    
    @Column(name="numero_boleta")
    private String numeroBoleta;  
    
    @Column(name="numero_serie")
    private String numeroSerie;
    
    @Column(name="total_deuda")
    private BigDecimal totalDeuda;
    
    @Column(name="adelanto")
    private BigDecimal adelanto;
    
    @Column(name="saldo_deuda")
    private BigDecimal saldoDeuda;
    
    @Column(name="tipo_pago")
    private String tipoPago;
    
    @Column(name="documento_transferencia")
    private String documentoTransferencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="deudores_id", nullable = false)
    private Deudores deudores;
    
    @Transient
    private Long deudoresId;
}
