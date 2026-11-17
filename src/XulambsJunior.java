public class XulambsJunior implements IFidelidade {
    private static final double MIN_VALOR = 50;
    private static final double DESCONTO = 2;

    @Override
    public double descontoPedido(Pedido pedido) {
        double precoOriginal = pedido.precoAPagar();
        return (precoOriginal) > MIN_VALOR?DESCONTO : 0 ;    
    }

    @Override 
    public String toString(){
        return "Xulambs Júnior";
    }
    
}
