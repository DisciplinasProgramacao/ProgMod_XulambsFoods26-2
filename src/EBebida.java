public enum EBebida implements  IProduto{

    ÁGUA_DA_CASA(0, "Água da casa"),
    ÁGUA_COM_GAS(4, "Água com gás"),
    CHÁ_COM_GAS(6, "Chá com gás"),
    SUCO(9, "Suco natural"),
    TAÇA_DE_VINHO(15, "Taça de vinho");

    private String nome;
    private double valor;

    EBebida(double preco, String desc){
        nome = desc;
        valor = preco;
    }
    
    
    @Override
    public double valorFinal() {
        return valor;
    }

    @Override 
    public String toString(){
        return String.format("%s\nTOTAL: R$ %.2f", nome, valor);
    }
    
}