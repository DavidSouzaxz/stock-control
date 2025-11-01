package com.challenge.stock.control.controller;

import com.challenge.stock.control.dto.produto.ProdutoRequestDTO;
import com.challenge.stock.control.dto.produto.ProdutoResponseDTO;
import com.challenge.stock.control.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listAllProdutos(){
        List<ProdutoResponseDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> listByIdProdutos(@PathVariable Long id){
        ProdutoResponseDTO produto = produtoService.findById(id);
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produto = produtoService.register(produtoRequestDTO);
        return ResponseEntity.ok().body(produto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produto = produtoService.update(id, produtoRequestDTO);
        return ResponseEntity.ok().body(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
