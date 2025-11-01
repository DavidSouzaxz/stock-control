package com.challenge.stock.control.dto.pedido;

import com.challenge.stock.control.entity.Cliente;
import com.challenge.stock.control.entity.Produto;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long idCliente;
    private String descricao;
    private List<PedidoQuantityDTO> produtos;
}
