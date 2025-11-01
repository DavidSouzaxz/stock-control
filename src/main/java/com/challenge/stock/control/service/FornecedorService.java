package com.challenge.stock.control.service;

import com.challenge.stock.control.dto.fornecedor.FornecedorRequestDTO;
import com.challenge.stock.control.dto.fornecedor.FornecedorResponseDTO;
import com.challenge.stock.control.entity.Fornecedor;
import com.challenge.stock.control.entity.Produto;
import com.challenge.stock.control.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<FornecedorResponseDTO> findAll(){
        return fornecedorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FornecedorResponseDTO findById(Long id){
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor is not presiting"));
        return convertToDTO(fornecedor);
    }


    public FornecedorResponseDTO register(FornecedorRequestDTO  fornecedorRequestDTO){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorRequestDTO.getNome());
        fornecedor.setEmail(fornecedorRequestDTO.getEmail());
        fornecedor.setCnpj(fornecedorRequestDTO.getCnpj());
        fornecedor.setTelefone(fornecedorRequestDTO.getTelefone());
        fornecedor.setRazaoSocial(fornecedorRequestDTO.getRazaoSocial());
        fornecedorRepository.save(fornecedor);
        return convertToDTO(fornecedor);
    }

    public FornecedorResponseDTO update(Long id,FornecedorRequestDTO  fornecedorRequestDTO){
        Fornecedor fornecedorPresiting = fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor is not presiting"));

        fornecedorPresiting.setNome(fornecedorRequestDTO.getNome());
        fornecedorPresiting.setEmail(fornecedorRequestDTO.getEmail());
        fornecedorPresiting.setCnpj(fornecedorRequestDTO.getCnpj());
        fornecedorPresiting.setTelefone(fornecedorRequestDTO.getTelefone());
        fornecedorPresiting.setRazaoSocial(fornecedorRequestDTO.getRazaoSocial());
        fornecedorRepository.save(fornecedorPresiting);
        return convertToDTO(fornecedorPresiting);
    }

    public void delete(Long id){
        fornecedorRepository.deleteById(id);
    }

    public FornecedorResponseDTO convertToDTO(Fornecedor fornecedor){
        FornecedorResponseDTO dto = new FornecedorResponseDTO();
        dto.setId(fornecedor.getId());
        dto.setNome(fornecedor.getNome());
        dto.setCnpj(fornecedor.getCnpj());
        dto.setEmail(fornecedor.getEmail());
        dto.setTelefone(fornecedor.getTelefone());
        dto.setRazaoSocial(fornecedor.getRazaoSocial());
        return dto;
    }
}
