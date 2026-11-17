public enum ESobremesa implements IProduto{
    BRIGADEIRO("Brigadeiro de colher", 8),
    DOCE_DE_LEITE("Doce de leite no pires", 9),
    PUDIM("Pudim de leite condensado", 10);

    private double valor;
    private String descricao;
    
    ESobremesa(String nome, double preco){
        descricao = nome;
        valor = preco;
    }

    @Override
    public double valorFinal() {
        return valor;
    }

    @Override 
    public String toString(){
        return String.format("%s\nTOTAL: R$ %.2f", descricao, valor);
    }

    
}
