package com.challenge.stock.control.controller;

import com.challenge.stock.control.dto.cliente.ClienteRequestDTO;
import com.challenge.stock.control.dto.pedido.PedidoRequestDTO;
import com.challenge.stock.control.dto.pedido.PedidoResponseDTO;
import com.challenge.stock.control.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        List<PedidoResponseDTO> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<byte[]> gerarExtratoPdf(@PathVariable Long id) {
        byte[] pdf = pedidoService.generateExtratoPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "pedido-" + id + "-extrato.pdf");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pdf);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedido = pedidoService.register(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
//        PedidoResponseDTO cliente = pedidoService.update(id,clienteRequestDTO);
//        return ResponseEntity.ok(cliente);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
