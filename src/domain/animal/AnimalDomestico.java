package domain.animal;

public class AnimalDomestico extends Animal {

    public AnimalDomestico(String codigo, String nombre, String especie, String raza,
                           double altura, double peso, int edad, boolean necesitaAtencionMedica) {
        super(codigo, nombre, especie, raza, altura, peso, edad, necesitaAtencionMedica);
    }

    @Override
    public boolean puedeSerAdoptado() {
        return !enTratamientoMedico();
    }
}
