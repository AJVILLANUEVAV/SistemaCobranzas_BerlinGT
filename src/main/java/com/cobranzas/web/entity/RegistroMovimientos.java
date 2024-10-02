package com.cobranzas.web.entity;


import lombok.Data;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "registro_movimientos")
public class RegistroMovimientos {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_movimientos")
    private Long idRegistroMovimientos;

    @Column(name = "resumen")
    private String resumen;

    @Column(name = "pago")
    private BigDecimal pago;

    @Column(name = "adelanto")
    private BigDecimal adelanto;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "saldo_total")
    private BigDecimal saldoTotal;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_deudor", nullable = false)
    private Deudores deudores;
    
    @Transient
    private Long id_deudor;
}
