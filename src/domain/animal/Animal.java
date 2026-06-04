package domain.animal;

import domain.historial.HistoriaClinica;

public abstract class Animal {

    private int id;
    private String codigo;
    private String nombre;
    private String especie;
    private String raza;
    private double altura;
    private double peso;
    private int edad;
    private boolean necesitaAtencionMedica;
    private HistoriaClinica historiaClinica;

    public Animal(String codigo, String nombre, String especie, String raza,
                  double altura, double peso, int edad, boolean necesitaAtencionMedica) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.necesitaAtencionMedica = necesitaAtencionMedica;
        this.historiaClinica = new HistoriaClinica(codigo);
    }

    public abstract boolean puedeSerAdoptado();

    public boolean enTratamientoMedico() {
        return necesitaAtencionMedica || historiaClinica.tieneAtencionActivaEnCurso();
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public double getAltura() { return altura; }
    public double getPeso() { return peso; }
    public int getEdad() { return edad; }
    public boolean isNecesitaAtencionMedica() { return necesitaAtencionMedica; }
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }

    public void setId(int id) { this.id = id; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setAltura(double altura) { this.altura = altura; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setNecesitaAtencionMedica(boolean necesitaAtencionMedica) {
        this.necesitaAtencionMedica = necesitaAtencionMedica;
    }
}
