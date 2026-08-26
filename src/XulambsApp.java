import java.util.LinkedList;
import java.util.List;

public class XulambsApp {
    private List<Pizza> listaPizzas;

    private void limparTela() {
        IO.print("\033[H\033[2J");
    }

    private void pausa() {
        IO.readln("Digite <ENTER> para continuar");
        limparTela();
    }

    private int exibirMenu() {
        IO.println("XULAMBS PIZZA - v0.1");
        IO.println("====================");
        IO.println("1 - Comprar pizza");
        IO.println("2 - Ver todas as pizza");
        IO.println("0 - Sair");
        return Integer.parseInt(IO.readln("Digite sua opção: "));
    }

    void comprarPizza() {
        int adicionais = Integer.parseInt(IO.readln("Quantos ingredientes? "));
        Pizza nova = new Pizza(adicionais);
        mostrarNota(nova);
        listaPizzas.add(nova);
    }

    void mostrarNota(Pizza pizza) {
        IO.println("Pizza comprada:");
        IO.println(pizza.gerarCupom());
        IO.println("====================");
    }

    void mostrarPizzas() {
        for (Pizza pizza : listaPizzas) {
            mostrarNota(pizza);
        }
    }
    
    void main() {
        int opcao;
        listaPizzas = new LinkedList<>();

        do {
            opcao = exibirMenu();

            switch (opcao) {
                case 1 -> comprarPizza();
                case 2 -> mostrarPizzas();
                case 0 -> IO.println("Encerrando!");
                default -> IO.println("Opção inválida");
            }
            pausa();
        } while (opcao != 0);
        

    }

}
