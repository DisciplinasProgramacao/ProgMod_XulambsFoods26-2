public class XulambsSenior implements  IFidelidade{

    private static final double PCT_DESCONTO = 0.15;
    private static final int COMPRAS_CUPOM = 6;
    private static final double VALOR_CUPOM = 10;
    private int numeroDaCompra = 0;

    @Override
    public double descontoPedido(Pedido pedido) {
        double precoOriginal = pedido.precoAPagar();
        double desconto = precoOriginal * PCT_DESCONTO;
        if(numeroDaCompra % COMPRAS_CUPOM == 0)
            desconto += VALOR_CUPOM;
        numeroDaCompra++;
        return desconto;    
    }
    
}
