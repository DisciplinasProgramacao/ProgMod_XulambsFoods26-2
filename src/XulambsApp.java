import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class XulambsApp {
    private List<Pedido> listaPedidos;
    private  List<Cliente> listaClientes;

    void limparTela() {
        IO.print("\033[H\033[2J");
    }

    void pausa(){
        IO.readln("Digite <ENTER> para continuar");
        limparTela();
    }

    int lerNumero(String mensagem){
        return Integer.parseInt(IO.readln(mensagem));
    }

    void config(){
        listaClientes = GeradorClientes.gerarClientes();
        listaPedidos = GeradorPedidos.gerarPedidos(LocalDate.now().minusDays(100), LocalDate.now(), listaClientes, 12);
        listaPedidos = new LinkedList<>();

    }

    void cabecalho(){
        limparTela();
        IO.println("XULAMBS FOODS - v0.7");
        IO.println("=====================");
        IO.println("Pizzas vendidas hoje: " +
                    Pizza.getPizzasVendidas());
    }

    int exibirMenu() {
        cabecalho();
        IO.println("1 - Abrir pedido");
        IO.println("2 - Alterar pedido");
        IO.println("3 - Relatório de um pedido");
        IO.println("4 - Encerrar pedido");
        IO.println("===========================");
        IO.println("5 - Lista clientes");
        IO.println("0 - Sair");
        return lerNumero("Digite sua opção: ");
    }

    private void listarClientes(){
        cabecalho();
        IO.println("Lista de clientes Xulambs:");
        for (Cliente cliente : listaClientes) {
            IO.println(cliente); 
        }
    }
    private IProduto comprarSobremesa() {
        ESobremesa[] sobremesas = ESobremesa.values();
        cabecalho();
        IO.println("Sobremesas Xulâmbicas:");
        for (int i = 0; i < sobremesas.length; i++) {
            IO.println(i+1 + " - "+sobremesas[i]);
        }
        int opcao = lerNumero("Sua escolha:");
        return sobremesas[opcao-1];
    }
   
    private IPersonalizavel incluirIngredientes(IPersonalizavel item){
        int adicionais = lerNumero("Quantos ingredientes? ");
        try {
            item.adicionarIngredientes(adicionais);    
        } catch (IllegalArgumentException e) {
            IO.println(e.getMessage());
        } catch (IllegalStateException e){
            IO.println(e.getMessage());
        }
        return item;
    }

    private IPersonalizavel comprarSanduiche() {
        cabecalho();
        IO.println("Comprando um Xulambs Super Burger:");
        
        Sanduiche sanduiche = new Sanduiche();

        String querCombo = IO.readln("Quer combo com fritas?(s/n)");
        if(querCombo.toLowerCase().equals("s"))
            sanduiche.setCombo(true);

        return sanduiche;
    }

    Pizza comprarPizza(){
        cabecalho();
        Pizza novaPizza = new Pizza();

        EBorda borda = escolherBorda();
        novaPizza.adicionarBorda(borda);
        
        exibirRelatorio(novaPizza);
        return novaPizza;
    }

    EBorda escolherBorda(){
        EBorda[] bordas = EBorda.values();
        int i = 1;
        IO.println("Escolha sua borda: ");
        for (EBorda eBorda : bordas) {
            IO.println(String.format("%d - Borda %s", i, eBorda.getNome()));
            i++;
        }
        int escolha = lerNumero("Digite sua opção: ");
        return bordas[escolha-1];
    }
   
    void armazenarPedido(Pedido pedido){
        if(pedido != null)
            listaPedidos.add(pedido);
    }

    PedidoEntrega criarPedidoEntrega(){
        String dist = IO.readln("Qual a distância da entrega? ");
        double distancia = Double.parseDouble(dist);
        return new PedidoEntrega(distancia);
    }
    
    Pedido escolherTipoPedido(){
        cabecalho();
        IO.println("Escolha o tipo do pedido: ");
        IO.println("1 - Local ");
        IO.println("2 - Para entrega");
        int escolha = lerNumero("Digite sua opção: ");
        return switch (escolha) {
            case 1 -> new PedidoLocal(LocalDate.now());
            case 2 -> criarPedidoEntrega();
            default -> null;
        };
    }
   
    void abrirPedido(){
        Pedido novoPedido = escolherTipoPedido();
        String novoItem;
        do {
            IProduto produto = comprarProduto();
            novoPedido.adicionarItem(produto);
            novoItem = IO.readln("Algo mais? (s/n)");
        } while (novoItem.equals("s"));
        exibirRelatorio(novoPedido);
        armazenarPedido(novoPedido);
    }

    private IProduto comprarProduto() {
        cabecalho();
        IO.println("Escolha seu produto:");
        IO.println("1 - Pizzas");
        IO.println("2 - Sanduíches");
        IO.println("3 - Sobremesas");
        int escolha = lerNumero("Sua escolha: ");
        return switch(escolha){
            case 1,2 -> comprarPersonalizavel(escolha);
            case 3 -> comprarSobremesa();
            default -> null;
        };
    }
    
    

    private IProduto comprarPersonalizavel(int escolha) {
        IPersonalizavel item = switch(escolha){
            case 1 -> comprarPizza();
            case 2 -> comprarSanduiche();
            default -> null;
        };
        incluirIngredientes(item);
        return (IProduto)item;
    }

    void alterarPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            Pizza pizza = comprarPizza();
            buscado.adicionarItem(pizza);
            exibirRelatorio(buscado);
        }
    }

    void relatorioPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            exibirRelatorio(buscado);
        }
    }

     void encerrarPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            buscado.fecharPedido();
            exibirRelatorio(buscado);
        }
    }

    Object localizar(){
        cabecalho();
        IO.println("LOCALIZAÇÃO DE PEDIDOS\n");
        int codigo = lerNumero("Código do pedido: ");
        Object localizado = null;
        for (int i = 0; i < listaPedidos.size() && localizado == null; i++) {
            Object candidato = listaPedidos.get(i);
            if(candidato.hashCode() == codigo){
                localizado = candidato;
            }
        }
        return localizado;            
    }

    void exibirRelatorio(Object objeto){
        cabecalho();
        IO.println("RELATÓRIO:\n");
        String mensagem = "Objeto não encontrado";
        if(objeto != null)
            mensagem = objeto.toString();
       
        IO.println(mensagem);
    }

    //  void mostrarNota(Pizza pizza){
    //     IO.println("Pizza comprada:");
    //     IO.println(pizza.toString());
    //     IO.println("=====================");
    // }

    void main(){
        int opcao;
        config();
        do {
            opcao = exibirMenu();
            switch (opcao) {
                case 1 -> abrirPedido();
                case 2 -> alterarPedido();
                case 3 -> relatorioPedido();
                case 4 -> encerrarPedido();
                case 5 -> listarClientes();
                case 0 -> IO.println("Encerrando!");
                default -> IO.println("Opção inválida");
            }   
            pausa(); 
        } while (opcao != 0);
    }
}
