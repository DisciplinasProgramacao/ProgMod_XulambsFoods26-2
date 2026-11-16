import java.util.List;

public interface IFidelidade {
    public double descontoPedido(Pedido pedido);

    public static IFidelidade definirCategoria(List<Pedido> pedidos){
        double[] valores =    {0,29,44, Double.POSITIVE_INFINITY};
        double[] quantidade = {0,10,20, Double.POSITIVE_INFINITY};
        IFidelidade[] categorias = 
            {new XulambsJunior(), new XulambsPleno(), new XulambsSenior(), new XulambsSenior()};
        
        int quantidadePedidos = pedidos.size();
        double mediaPedidos = 0;
        for (Pedido pedido : pedidos) {
            mediaPedidos += pedido.precoAPagar();
        }
        mediaPedidos = mediaPedidos/quantidadePedidos;
        int pos = 0;
        while (quantidade[pos] < quantidadePedidos) {
            pos++;
        }
        if(mediaPedidos < valores[pos]){
            pos--;
        }
        
        return categorias[pos];
    }
}
