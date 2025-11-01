package com.challenge.stock.control.dto.pedido;

import com.challenge.stock.control.entity.Cliente;
import com.challenge.stock.control.entity.Produto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class PedidoResponseDTO {
    private Long id;
    private Cliente cliente;
    private String descricao;
    private double valorTotal;
    private List<Produto> produtos;
}
