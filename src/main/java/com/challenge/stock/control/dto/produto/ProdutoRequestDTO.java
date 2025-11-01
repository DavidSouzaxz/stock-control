package com.challenge.stock.control.dto.produto;

import com.challenge.stock.control.entity.Fornecedor;
import lombok.Data;

@Data
public class ProdutoRequestDTO {
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private Long idFornecedor;
}
