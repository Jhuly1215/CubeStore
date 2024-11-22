package Modelos;

public class Catalogo {
    private int codigo; // producto
    private String nombre; // producto
    private double precio = 0.0; // producto
    private String marca; // producto
    private String ruta; // imagen del producto
    private int idTipo; // tipo de producto
    private int idSubtipo; //(2x2,3x3... solo para cubos y mods)
    private double alto = 0.0; //(solo para cubos y mods)
    private double ancho = 0.0; //(solo para cubos y mods)
    private double largo = 0.0;//(solo para cubos y mods)
    private double tamano = 0.0; //(solo para accesorios)

    public Catalogo() {}
    public Catalogo(int codigo, String nombre, double precio, String marca, String ruta, int idTipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.ruta = ruta;
        this.idTipo = idTipo;
    }
    
    //Constuctor de cubo y mod
    public Catalogo(int codigo, String nombre, double precio, String marca, String ruta, int idTipo, int idSubtipo,
    		double alto, double ancho, double largo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.ruta = ruta;
        this.idTipo = idTipo; 
        this.idSubtipo=idSubtipo;
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
    }
    
    //Constuctor de accesorio o lubricante 
    public Catalogo(int codigo, String nombre, double precio, String marca, String ruta, int idTipo, double tamano) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.ruta = ruta;
        this.idTipo = idTipo;
        this.tamano=tamano;
    }

 // Getters y Setters con validaciones opcionales para dimensiones
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    
    
    public int getIdSubtipo() { return idSubtipo; }
    public void setIdSubtipo(int idSubtipo) { this.idSubtipo = idSubtipo; }

    
    
    public double getAlto() { return alto; }
    public void setAlto(double alto) {
        if (alto < 0) throw new IllegalArgumentException("El alto debe ser positivo.");
        this.alto = alto;
    }

    public double getAncho() { return ancho; }
    public void setAncho(double ancho) {
        if (ancho < 0) throw new IllegalArgumentException("El ancho debe ser positivo.");
        this.ancho = ancho;
    }

    public double getLargo() { return largo; }
    public void setLargo(double largo) {
        if (largo < 0) throw new IllegalArgumentException("El largo debe ser positivo.");
        this.largo = largo;
    }

    public double getTamano() { return tamano; }
    public void setTamano(double tamano) {
        if (tamano < 0) throw new IllegalArgumentException("El tamaño debe ser positivo.");
        this.tamano = tamano;
    }
    
    
    @Override
    public String toString() { //debuging nomas
        return "Catalogo{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", marca='" + marca + '\'' +
                ", ruta='" + ruta + '\'' +
                ", idTipo=" + idTipo +
                ", idSubtipo=" + idSubtipo +
                ", alto=" + alto +
                ", ancho=" + ancho +
                ", largo=" + largo +
                ", tamano=" + tamano +
                '}';
    }
}

