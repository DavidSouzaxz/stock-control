package com.challenge.stock.control.controller;

import com.challenge.stock.control.dto.fornecedor.FornecedorRequestDTO;
import com.challenge.stock.control.dto.fornecedor.FornecedorResponseDTO;
import com.challenge.stock.control.entity.Fornecedor;
import com.challenge.stock.control.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fornecedor")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> findAll(){
        List<FornecedorResponseDTO> list = fornecedorService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> findById(@PathVariable Long id){
        FornecedorResponseDTO fornecedor = fornecedorService.findById(id);
        return ResponseEntity.ok().body(fornecedor);
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> save(@RequestBody FornecedorRequestDTO fornecedorRequestDTO){
        FornecedorResponseDTO fornecedor = fornecedorService.register(fornecedorRequestDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> update (@PathVariable Long id, @RequestBody FornecedorRequestDTO fornecedorRequestDTO){
        FornecedorResponseDTO fornecedor = fornecedorService.update(id, fornecedorRequestDTO);
        return ResponseEntity.ok().body(fornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
