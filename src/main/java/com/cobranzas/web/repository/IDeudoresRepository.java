package com.cobranzas.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cobranzas.web.entity.Deudores;


public interface IDeudoresRepository extends JpaRepository<Deudores, Long>{

	@Query("FROM Deudores d WHERE d.nombres LIKE :name OR d.apellidos LIKE :name")
    public List<Deudores> findByNameContaining(@Param("name") String name);
}
