package com.challenge.stock.control.repository;

import com.challenge.stock.control.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
