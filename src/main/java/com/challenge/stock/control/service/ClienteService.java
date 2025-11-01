package com.challenge.stock.control.service;

import com.challenge.stock.control.dto.cliente.ClienteRequestDTO;
import com.challenge.stock.control.dto.cliente.ClienteResponseDTO;
import com.challenge.stock.control.entity.Cliente;
import com.challenge.stock.control.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;


    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponseDTO> findAll(){
        return clienteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO findById(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente is not preseting"));

        return convertToDTO(cliente);
    }

    public ClienteResponseDTO save(ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = new Cliente();

        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setCpf(clienteRequestDTO.getCpf());
        clienteRepository.save(cliente);
        return convertToDTO(cliente);
    }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO){
        Cliente clienteIsUpdated = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente is not preseting"));

        clienteIsUpdated.setNome(clienteRequestDTO.getNome());
        clienteIsUpdated.setCpf(clienteRequestDTO.getCpf());
        clienteRepository.save(clienteIsUpdated);
        return convertToDTO(clienteIsUpdated);
    }

    public void delete(Long id){
        clienteRepository.deleteById(id);
    }


    public ClienteResponseDTO convertToDTO(Cliente cliente){
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setId(cliente.getId());
        clienteResponseDTO.setNome(cliente.getNome());
        clienteResponseDTO.setCpf(cliente.getCpf());
        return clienteResponseDTO;
    }
}
