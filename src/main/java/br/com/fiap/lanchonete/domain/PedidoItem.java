package br.com.fiap.lanchonete.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pedido_item")
public class PedidoItem {
    @Id
    @GeneratedValue(generator="pedidoItemIdGen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="pedidoItemIdGen", sequenceName="sq_tb_pedido_item", initialValue=1, allocationSize=1)
    private Long id;
}
