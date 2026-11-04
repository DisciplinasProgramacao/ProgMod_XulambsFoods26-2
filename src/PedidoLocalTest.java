import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoLocalTest {
    Pedido pedido;
    Pizza pizzaVazia;

    @BeforeEach 
    public void setUp(){
        pedido = new PedidoLocal();
        pizzaVazia = new Pizza();
        pedido.adicionarItem(pizzaVazia);
    }
    
    @Test
    public void naoAdicionaPizzaEmPedidoFechado(){
        //Arrange
         pedido.fecharPedido();
        //Act
        int quantidade = pedido.adicionarItem(new Pizza());
        //Assert
        assertEquals(1, quantidade);
    }

    @Test
    public void adicionaPizzasEmPedidoAberto(){
        //Arrange
         pedido.adicionarItem(pizzaVazia);
        //Act
        int quantidade = pedido.adicionarItem(new Pizza());
        //Assert
        assertEquals(3, quantidade);
    }

    @Test 
    public void calculaPrecoComUmaPizza(){
        //Act
        double preco = pedido.precoAPagar();
        //Assert
        assertEquals(29d, preco, 0.01);
    }

    @Test 
    public void calculaPrecoComVariasPizzas(){
        //Arrange
        Pizza comIngredientes = new Pizza(2);
        pedido.adicionarItem(comIngredientes);
        //Act
        double preco = pedido.precoAPagar();
        //Assert
        assertEquals(68d, preco, 0.01);
    }

    @Test 
    public void gerarRelatorioComDetalhes(){
        //Arrange
        pedido.adicionarItem(new Pizza());
        //Act
        String cupom = pedido.toString();
        assertTrue(
            cupom.contains("2 pizzas") &&
            cupom.contains("58,00")
        );    
    }
}
