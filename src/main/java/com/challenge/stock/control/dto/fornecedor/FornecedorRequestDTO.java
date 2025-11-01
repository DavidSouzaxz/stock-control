package com.challenge.stock.control.dto.fornecedor;

import lombok.Data;

@Data
public class FornecedorRequestDTO {
    private String nome;
    private String cnpj;
    private String razaoSocial;
    private String email;
    private String telefone;
}
