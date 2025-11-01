package com.challenge.stock.control.repository;

import com.challenge.stock.control.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
