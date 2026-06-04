package domain.animal;

public class AnimalSalvaje extends Animal {

    public AnimalSalvaje(String codigo, String nombre, String especie, String raza,
                         double altura, double peso, int edad, boolean necesitaAtencionMedica) {
        super(codigo, nombre, especie, raza, altura, peso, edad, necesitaAtencionMedica);
    }

    @Override
    public boolean puedeSerAdoptado() {
        return false;
    }
}
