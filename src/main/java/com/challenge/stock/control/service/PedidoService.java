package com.challenge.stock.control.service;

import com.challenge.stock.control.dto.pedido.PedidoQuantityDTO;
import com.challenge.stock.control.dto.pedido.PedidoRequestDTO;
import com.challenge.stock.control.dto.pedido.PedidoResponseDTO;
import com.challenge.stock.control.entity.Cliente;
import com.challenge.stock.control.entity.Pedido;
import com.challenge.stock.control.entity.Produto;
import com.challenge.stock.control.repository.ClienteRepository;
import com.challenge.stock.control.repository.PedidoRepository;
import com.challenge.stock.control.repository.ProdutoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;


    private final ClienteRepository clienteRepository;

    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<PedidoResponseDTO> findAll(){
        return pedidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findById(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido is not presiting"));

        return convertToDTO(pedido);
    }

    public PedidoResponseDTO register(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Produto> produtosParaPedido = new ArrayList<>();
        double valorTotal = 0.00;

        for(PedidoQuantityDTO pedidoQuantityDTO : pedidoRequestDTO.getProdutos()){
            Long idProduto = pedidoQuantityDTO.getIdProduto();
            Produto produto = produtoRepository.findById(idProduto)
                    .orElseThrow(() -> new RuntimeException("Produto com id " + idProduto + " não encontrado"));

            int qtd = pedidoQuantityDTO.getQuantidade() <= 0 ? 1 : pedidoQuantityDTO.getQuantidade();


            int estoqueAtual = produto.getEstoque();
            if (estoqueAtual < qtd) {
                throw new RuntimeException("Estoque insuficiente para o produto com id " + idProduto);
            }

            produto.setEstoque(estoqueAtual - qtd);
            produtoRepository.save(produto);

            for (int i = 0; i < qtd; i++) {
                produtosParaPedido.add(produto);
            }
            valorTotal += produto.getPreco() * qtd;
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDescricao(pedidoRequestDTO.getDescricao());
        pedido.setProdutos(produtosParaPedido);
        pedido.setValorTotal(valorTotal);

        pedidoRepository.save(pedido);
        return convertToDTO(pedido);
    }

    public void delete(Long id){
        pedidoRepository.deleteById(id);
    }

    public byte[] generateExtratoPdf(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Calcular quantidades por produto (preservando ordem de inserção)
        Map<Long, Integer> quantidadePorProduto = new LinkedHashMap<>();
        Map<Long, Produto> produtoPorId = new LinkedHashMap<>();
        if (pedido.getProdutos() != null) {
            for (Produto p : pedido.getProdutos()) {
                produtoPorId.putIfAbsent(p.getId(), p);
                quantidadePorProduto.merge(p.getId(), 1, Integer::sum);
            }
        }

        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 12, Font.NORMAL);

            document.add(new Paragraph("Extrato do Pedido", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Pedido ID: " + pedido.getId(), normal));
            Cliente c = pedido.getCliente();
            if (c != null) {
                document.add(new Paragraph("Cliente: " + c.getNome(), normal));
                document.add(new Paragraph("CPF/Email: " + (c.getCpf() != null ? c.getCpf() : ""), normal));
            }
            document.add(new Paragraph("Descrição: " + (pedido.getDescricao() != null ? pedido.getDescricao() : ""), normal));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidths(new float[]{1f, 4f, 2f, 2f, 2f});
            table.addCell("ID");
            table.addCell("Produto");
            table.addCell("Preço Unit.");
            table.addCell("Quantidade");
            table.addCell("Subtotal");

            double total = 0.0;
            for (Map.Entry<Long, Integer> entry : quantidadePorProduto.entrySet()) {
                Long pid = entry.getKey();
                int qtd = entry.getValue();
                Produto prod = produtoPorId.get(pid);
                double preco = prod != null ? prod.getPreco() : 0.0;
                double subtotal = preco * qtd;
                total += subtotal;

                table.addCell(String.valueOf(pid));
                table.addCell(prod != null ? prod.getNome() : "N/A");
                table.addCell(currency.format(preco));
                table.addCell(String.valueOf(qtd));
                table.addCell(currency.format(subtotal));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Valor Total: " + currency.format(total), titleFont));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }

    public PedidoResponseDTO convertToDTO(Pedido pedido){
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedido.getId());
        pedidoResponseDTO.setCliente(pedido.getCliente());
        pedidoResponseDTO.setDescricao(pedido.getDescricao());
        pedidoResponseDTO.setValorTotal(pedido.getValorTotal());
        List<Produto> produtosUnicos = pedido.getProdutos() == null ? new ArrayList<>() :
                pedido.getProdutos().stream()
                        .collect(Collectors.toMap(Produto::getId, p -> p, (a, b) -> a))
                        .values()
                        .stream()
                        .collect(Collectors.toList());

        pedidoResponseDTO.setProdutos(produtosUnicos);
        return pedidoResponseDTO;
    }
}
