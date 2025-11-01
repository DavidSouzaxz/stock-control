package com.challenge.stock.control.service;

import com.challenge.stock.control.dto.produto.ProdutoRequestDTO;
import com.challenge.stock.control.dto.produto.ProdutoResponseDTO;
import com.challenge.stock.control.entity.Fornecedor;
import com.challenge.stock.control.entity.Produto;
import com.challenge.stock.control.repository.FornecedorRepository;
import com.challenge.stock.control.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(ProdutoRepository produtoRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<ProdutoResponseDTO> findAll(){
        return produtoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO findById(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtos is not presiting"));
        return convertToDTO(produto);
    }

    public ProdutoResponseDTO register(ProdutoRequestDTO produtoRequestDTO){
        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.getNome());
        produto.setDescricao(produtoRequestDTO.getDescricao());
        produto.setPreco(produtoRequestDTO.getPreco());
        produto.setEstoque(produtoRequestDTO.getEstoque());

        Fornecedor fornecedor = fornecedorRepository.findById(produtoRequestDTO.getIdFornecedor())
                .orElseThrow(() ->  new RuntimeException("Supplier is not presiting"));

        produto.setFornecedor(fornecedor);
        produtoRepository.save(produto);
        return convertToDTO(produto);
    }

    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO produtoRequestDTO){
        Produto produtoUpdate =  produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtos is not presiting"));

        produtoUpdate.setNome(produtoRequestDTO.getNome());
        produtoUpdate.setDescricao(produtoRequestDTO.getDescricao());
        produtoUpdate.setPreco(produtoRequestDTO.getPreco());
        produtoUpdate.setEstoque(produtoRequestDTO.getEstoque());
        Fornecedor fornecedor = fornecedorRepository.findById(produtoRequestDTO.getIdFornecedor())
                .orElseThrow(() ->  new RuntimeException("Supplier is not presiting"));

        produtoUpdate.setFornecedor(fornecedor);
        produtoRepository.save(produtoUpdate);
        return convertToDTO(produtoUpdate);
    }

    public void delete(Long id){
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseDTO convertToDTO(Produto produto){
        ProdutoResponseDTO produtoResponseDTO = new ProdutoResponseDTO();
        produtoResponseDTO.setId(produto.getId());
        produtoResponseDTO.setDescricao(produto.getDescricao());
        produtoResponseDTO.setNome(produto.getNome());
        produtoResponseDTO.setPreco(produto.getPreco());
        produtoResponseDTO.setFornecedor(produto.getFornecedor());
        produtoResponseDTO.setEstoque(produto.getEstoque());
        return produtoResponseDTO;
    }

}


