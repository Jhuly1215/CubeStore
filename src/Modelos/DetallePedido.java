package Modelos;

public class DetallePedido {
    private int cDetallePedido;
    private int idPedido;
    private int codigoProducto;
    private int cantidad;

    // Constructor
    public DetallePedido(int cDetallePedido, int idPedido, int codigoProducto, int cantidad) {
        this.cDetallePedido = cDetallePedido;
        this.idPedido = idPedido;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public int getCDetallePedido() {
        return cDetallePedido;
    }

    public void setCDetallePedido(int cDetallePedido) {
        this.cDetallePedido = cDetallePedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "cDetallePedido=" + cDetallePedido +
                ", idPedido=" + idPedido +
                ", codigoProducto=" + codigoProducto +
                ", cantidad=" + cantidad +
                '}';
    }
}
