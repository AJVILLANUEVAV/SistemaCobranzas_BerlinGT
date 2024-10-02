package com.cobranzas.web.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cobranzas.web.entity.RegistroMovimientos;

public interface IRegistroMovimientosRepository extends JpaRepository<RegistroMovimientos, Long> {

	// @Query("SELECT rm.saldoTotal FROM RegistroMovimientos rm WHERE rm.deudores.id
	// = :idDeudor ORDER BY rm.idRegistroMovimientos DESC")
	@Query(value = "SELECT rm.saldo_total FROM registro_movimientos rm WHERE rm.id_deudor = :idDeudor ORDER BY rm.id_registro_movimientos DESC limit 1", nativeQuery = true)
	public BigDecimal obteniendoSaldoTotalByDeudorId(@Param("idDeudor") Long idDeudor);
}
