import java.util.LinkedList;
import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private List<Pedido> pedidos;

    public Cliente(String nome, int id){
        if(nome.length() == 0 || !nome.contains(" "))
            throw new IllegalArgumentException("Nome inválido");
        if(id <= 0 )
            throw new IllegalArgumentException("Documento inválido");

        this.nome = nome;
        this.id = id;
        pedidos = new LinkedList<>();
    }

    public int registrarPedido(Pedido novoPedido){
        if(novoPedido == null)
            throw new NullPointerException();
        pedidos.addLast(novoPedido);
        return  pedidos.size();
    }

    public double totalGasto(){
        double valor = 0d;
        for (Pedido pedido : pedidos) {
            valor += pedido.precoAPagar();
        }
        return  valor;
    }

    public String relatorioPedidos(){
        StringBuilder relatorio = new StringBuilder(toString()+"\n---------------\n");
        for (Pedido pedido : pedidos) {
            relatorio.append(pedido + "\n\n");
        }
        return relatorio.toString();
    }

    @Override 
    public int hashCode(){
        return id;
    }

    @Override 
    public String toString(){
        return String.format("%s (%d): Total Gasto: R$ %.2f", 
            nome, id, totalGasto());
    }
}
