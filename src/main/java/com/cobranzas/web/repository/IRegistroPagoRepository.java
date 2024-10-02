package com.cobranzas.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cobranzas.web.entity.RegistroDeuda;
import com.cobranzas.web.entity.RegistroPago;

public interface IRegistroPagoRepository extends JpaRepository<RegistroPago, Long>{

//	@Query("SELECT rp FROM RegistroPago rp INNER JOIN rp.deudores d WHERE CONCAT(d.nombres, ' ', d.apellidos) LIKE %:name%")
    @Query("SELECT rp FROM RegistroPago rp WHERE CONCAT(rp.deudores.nombres, ' ', rp.deudores.apellidos) LIKE %:name%")
    public List<RegistroPago> findByNameContaining(@Param("name") String name);
}
