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

@Data
@Entity
@Table(name = "registro_pago")
public class RegistroPago {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_pago")
    private Long idRegistroPago;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    @Column(name = "boleta")
    private String boleta;

    @Column(name = "serie")
    private String serie;

    @Column(name = "monto_pago")
    private BigDecimal montoPago;

    @Column(name = "tipo_pago")
    private String tipoPago;

    @Column(name = "documento_transferencia")
    private String documentoTransferencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deudor", nullable = false)
    private Deudores deudores;
    
    @Transient
    private Long id_deudor;
}
