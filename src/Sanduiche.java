public class Sanduiche implements IProduto, IPersonalizavel {
    private static final int MAX_ADICIONAIS = 5;
    private static final double VALOR_BASE = 18d;
    private static final double VALOR_ADICIONAL = 3d;
    private static final double VALOR_COMBO = 7d;

    private  int adicionais;
    private  boolean combo;

    public Sanduiche(){
        adicionais = 0;
        combo = false;
    }

    public boolean setCombo(boolean querCombo){
        combo = querCombo;
        return combo;
    }

    @Override
    public int adicionarIngredientes(int quantidade) {
        if(quantidade < 0)
            throw new IllegalArgumentException("Ingredientes negativos.");
        if(quantidade + adicionais > MAX_ADICIONAIS)
            throw new IllegalStateException("Máximo excedido.");

        adicionais += quantidade;
        return adicionais;
    }

    @Override
    public double valorFinal() {
        double valorCombo = (combo) ? VALOR_COMBO : 0;
        return VALOR_BASE + (adicionais * VALOR_ADICIONAL) + valorCombo;
    }
    
    @Override 
    public String toString(){
        StringBuilder cupom = new StringBuilder("Sanduíche com "+adicionais+" ingredientes");
        String ehCombo = combo? "e combo com fritas." : ".";
        cupom.append(ehCombo+"\n");
        cupom.append(String.format("\tValor base: R$ %.2f\n", VALOR_BASE));
        cupom.append(String.format("\t%d adicionais: R$ %.2f\n", adicionais, adicionais*VALOR_ADICIONAL));
        if(combo)
            cupom.append(String.format("\tCombo: R$ %.2f\n", VALOR_COMBO));
        cupom.append(String.format("TOTAL: R$ %.2f", valorFinal()));
        return  cupom.toString();
    }
}
