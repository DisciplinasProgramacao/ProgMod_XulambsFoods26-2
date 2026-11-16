import java.util.Random;

public class GeradorProdutos {
    static Random sorteio = new Random(42);
    static IProduto criar(int tipo){
        return switch(tipo){
            case 1 -> criarPizza();
            case 2 -> criarSanduiche();
            case 3 -> criarBebida();
            case 4 -> criarSobremesa();
            default -> null;
        };
    }

    static Pizza criarPizza(){
        int maxIngredientes = 8;
        Pizza p = new Pizza();
        int borda = sorteio.nextInt(EBorda.values().length);
        p.adicionarBorda(EBorda.values()[borda]);
        int adicionais = sorteio.nextInt(0, maxIngredientes+1);
        p.adicionarIngredientes(adicionais);
        return p;
    }

    static Sanduiche criarSanduiche(){
        int maxIngredientes = 5;
        Sanduiche s = new Sanduiche();
        int combo = sorteio.nextInt(1000);
        boolean ehCombo = combo > 728;
        int adicionais = sorteio.nextInt(0, maxIngredientes+1);
        s.adicionarIngredientes(adicionais);
        s.setCombo(ehCombo);
        return s;
    }

    static Bebida criarBebida(){
        int pos = sorteio.nextInt(EBebida.values().length);
        EBebida bebida = EBebida.values()[pos];
        return new Bebida(bebida);
    }

    static ESobremesa criarSobremesa(){
        int pos = sorteio.nextInt(ESobremesa.values().length);
        ESobremesa sobremesa = ESobremesa.values()[pos];
        return sobremesa;
    }
}