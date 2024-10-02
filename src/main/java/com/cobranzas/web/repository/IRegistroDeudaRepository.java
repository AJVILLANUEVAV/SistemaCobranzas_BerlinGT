package com.cobranzas.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cobranzas.web.entity.RegistroDeuda;

public interface IRegistroDeudaRepository extends JpaRepository<RegistroDeuda, Long>{

	// @Query("SELECT rp FROM RegistroPago rp WHERE CONCAT(rp.deudores.nombres, ' ', rp.deudores.apellidos) LIKE %:name%")
    //ver query para join
	@Query("SELECT rd FROM RegistroDeuda rd WHERE CONCAT(rd.deudores.nombres, ' ', rd.deudores.apellidos) LIKE %:name%")
    public List<RegistroDeuda> findByNameContaining(@Param("name") String name);
}
