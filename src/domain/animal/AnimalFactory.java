package domain.animal;

import domain.enums.TipoAnimal;

public final class AnimalFactory {

    private AnimalFactory() {
    }

    public static Animal crear(TipoAnimal tipo, String codigo, String nombre, String especie, String raza,
                               double altura, double peso, int edad, boolean necesitaAtencionMedica) {
        return switch (tipo) {
            case SALVAJE -> new AnimalSalvaje(codigo, nombre, especie, raza, altura, peso, edad, necesitaAtencionMedica);
            case DOMESTICO -> new AnimalDomestico(codigo, nombre, especie, raza, altura, peso, edad, necesitaAtencionMedica);
        };
    }
}
