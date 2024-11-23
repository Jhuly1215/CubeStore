package Modelos;

import java.util.Date;

public class Pedido {
    private int idPedido;
    private Date fecha;
    private double total;
    private int usuario;

    public Pedido(int idPedido, Date fecha, double total, int usuario) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.usuario = usuario;
    }

    private int codigoProducto;
    private String nombre;
    private double precio;
    private int cantidad;
    private double costo;

    // Constructor para el archivo
    public Pedido(int codigoProducto, String nombre, double precio, int cantidad, double costo) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.costo = costo;
    }
    
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    
    
    
    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fecha=" + fecha +
                ", total=" + total +
                ", usuario=" + usuario +
                '}';
    }



}
