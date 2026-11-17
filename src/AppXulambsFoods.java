import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class AppXulambsFoods {
    private List<Pedido> listaPedidos;
    private  List<Cliente> listaClientes;

    void limparTela() {
        IO.print("\033[H\033[2J");
    }

    void pausa() {
        IO.readln("Digite <ENTER> para continuar");
        limparTela();
    }

    int lerNumero(String mensagem) {
        int retorno;
        try {
            retorno = Integer.parseInt(IO.readln(mensagem));
        } catch (NumberFormatException excecao) {
            IO.println(excecao.getMessage());
            retorno = -1;
        }
        return retorno;
    }

    void config(){
        listaClientes = GeradorClientes.gerarClientes();
        listaPedidos = GeradorPedidos.gerarPedidos(LocalDate.now().minusDays(20), LocalDate.now(), listaClientes, 10);
    }

    void cabecalho() {
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
        IO.println("6 - Atualizar categorias");
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
        int adicionais;
        Pizza novaPizza = new Pizza();

        EBorda borda = escolherBorda();
        novaPizza.adicionarBorda(borda);

        adicionais = lerNumero("Quantos ingredientes? ");
        try {
            novaPizza.adicionarIngredientes(adicionais);
        } catch (InvalidParameterException e) {
            IO.println(e.getMessage());
            pausa();
            return comprarSobremesa();
        } catch (MaximoDeIngredientesException maisDoQueOMaximo) {
            IO.println(maisDoQueOMaximo.getMessage());
            String completar = IO.readln("Deseja completar a pizza com o máximo possível(1) ou cancelar(2)? ");
            if (completar.toLowerCase().equals("1"))
                maisDoQueOMaximo.completarPizza(novaPizza);
            else
                novaPizza = null;
        }

        exibirRelatorio(novaPizza);
        return novaPizza;
    }

    EBorda escolherBorda() {
        EBorda[] bordas = EBorda.values();
        EBorda bordaEscolhida = EBorda.TRADICIONAL;;
        int i = 1;
        IO.println("Escolha sua borda: ");
        for (EBorda eBorda : bordas) {
            IO.println(String.format("%d - Borda %s", i, eBorda.getNome()));
            i++;
        }
        int escolha = lerNumero("Digite sua opção: ");
        try {
            bordaEscolhida = bordas[escolha - 1];
        } catch (IndexOutOfBoundsException e) {
            IO.println("Valor inválido. Escolha novamente (obrigatório para comprar uma pizza.");
            return  escolherBorda();
        }

        return bordaEscolhida;
    }

    void armazenarPedido(Pedido pedido) {
        if (pedido != null)
            listaPedidos.add(pedido);
    }

    PedidoEntrega criarPedidoEntrega() {
        String dist = IO.readln("Qual a distância da entrega? ");
        double distancia = Double.parseDouble(dist);
        return new PedidoEntrega(distancia);
    }

    Pedido escolherTipoPedido() {
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

    void abrirPedido() {
        Pedido novoPedido = escolherTipoPedido();
        String novoItem;
        do {
            Pizza pizza = comprarPizza();
            try {
                novoPedido.adicionarPizza(pizza);           
            } catch (IllegalArgumentException e) {
                IO.println(e.getMessage());           
            }
            novaPizza = IO.readln("Mais pizza? (s/n)");
        } while (novaPizza.toLowerCase().equals("s"));
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
        Pedido buscado = (Pedido)localizar(listaPedidos);
        if(buscado != null){
            Pizza pizza = comprarPizza();
            buscado.adicionarItem(pizza);
            exibirRelatorio(buscado);
        }
    }

    void relatorioPedido(){
        Pedido buscado = (Pedido)localizar(listaPedidos);
        if(buscado != null){
            exibirRelatorio(buscado);
        }
    }

     void encerrarPedido(){
        Pedido buscado = (Pedido)localizar(listaPedidos);
        Cliente cliente = (Cliente)localizar(listaClientes);
        if(buscado != null){
            buscado.fecharPedido();
            if(cliente != null)
                cliente.registrarPedido(buscado);
            exibirRelatorio(buscado);
        }
    }

    Object localizar(List<?> lista){
        cabecalho();
        String classe = lista.getFirst().getClass().getName();
        IO.println("LOCALIZAÇÃO\n");
        int codigo = lerNumero("Código do "+classe+": ");
        Object localizado = null;
        for (int i = 0; i < lista.size() && localizado == null; i++) {
            Object candidato = lista.get(i);
            if(candidato.hashCode() == codigo){
                localizado = candidato;
            }
        }
        return localizado;
    }

    void exibirRelatorio(Object objeto) {
        cabecalho();
        IO.println();
        String mensagem = "";
        if (objeto != null)
            mensagem = objeto.toString();

        IO.println(mensagem);
    }

    //  void mostrarNota(Pizza pizza){
    //     IO.println("Pizza comprada:");
    //     IO.println(pizza.toString());
    //     IO.println("=====================");
    // }

    void atualizarClientes(){
        cabecalho();
        for (Cliente cliente : listaClientes) {
            cliente.atualizarCategoria();
        }
        IO.println("Categorias de fidelidade atualizadas!");
    }

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
                case 6 -> atualizarClientes();
                case 0 -> IO.println("Encerrando!");
                default -> IO.println("Opção inválida");
            }
            pausa();
        } while (opcao != 0);
    }
}
