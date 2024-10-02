package com.cobranzas.web.entity;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="deudores")
@Data
public class Deudores {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="nombres", nullable=false)
    private String nombres;
    
    @Column(name="apellidos")
    private String apellidos;
    
    @Column(name="correo")
    private String correo;  
    
    @Column(name="telefono")
    private String telefono;
    
    @Column(name="direccion")
    private String direccion;
    
    @Column(name="provincia")
    private String provincia;
    
    @OneToMany(mappedBy = "deudores",cascade = CascadeType.ALL)
    private List<RegistroDeuda> registro_deuda;
    
}
