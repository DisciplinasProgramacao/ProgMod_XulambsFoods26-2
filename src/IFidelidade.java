import java.util.List;

public interface IFidelidade {
    public double descontoPedido(Pedido pedido);

    public static IFidelidade definirCategoria(List<Pedido> pedidos){
        int[] quantidades = {0, 10,  20,  Integer.MAX_VALUE};
        double[] valores =  {0, 29d, 44d, Double.MAX_VALUE};
        IFidelidade[] categorias = {new XulambsJunior(), new XulambsPleno(), 
                                    new XulambsSenior(), null};
        int quantPedidos = pedidos.size();
        double valorMedio = 0;
        for (Pedido p : pedidos) {
            valorMedio += p.precoAPagar();
        }
        valorMedio = valorMedio / quantPedidos;
        int pos = 3;
        while (quantPedidos < quantidades[pos] || 
               valorMedio < valores[pos]) {
            pos--;
        }
        return categorias[pos];
    }
}
