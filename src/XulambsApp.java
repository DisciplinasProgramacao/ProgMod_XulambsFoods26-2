import java.util.List;

public class XulambsApp {
    static List<Pizza> pizzas;

    void main() {
        int opcao;
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1 -> comprarPizza();
                case 2 -> mostrarPizzas();
                case 0 -> IO.println("Encerrando");
                default -> IO.println("Opção Inválida");

            }
        } while (opcao == 0);
    }

    public static void printCabecalho() {
        IO.println("XULAMBS PIZZA v0.1");
        IO.println("==================");
    }

    private int menuPrincipal() {
        IO.println("XULAMBS PIZZA v0.1");
        IO.println("==================");
        IO.println("1 - Comprar uma pizza");
        IO.println("2 - Ver pizzas vendidas");
        IO.println("0 - Finalizar");

        return Integer.parseInt(IO.readln("Digite Sua opção: "));
    }

    private void comprarPizza() {
        printCabecalho();

        IO.println("Comprando uma Pizza");
        int adicionais = escolherIngredientes();
        Pizza novaPizza = new Pizza(adicionais);
        mostrarNota(novaPizza);
        pizzas.add(novaPizza);
    }

    private void mostrarNota(Pizza novaPizza) {
        IO.println("#################");
        IO.println(novaPizza.gerarCupom());
        IO.println("#################");

    }

    private void mostrarPizzas() {
        printCabecalho();

        for (Pizza pizza : pizzas) {
            mostrarNota(pizza);
            IO.println();
        }

    }

    int escolherIngredientes() {
        return Integer.parseInt(IO.readln("Quantos adicionais?: "));
    }
}
