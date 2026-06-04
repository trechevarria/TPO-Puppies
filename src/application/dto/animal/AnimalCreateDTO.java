package application.dto.animal;

import domain.enums.TipoAnimal;

public class AnimalCreateDTO {
    public TipoAnimal tipo;
    public double peso;
    public double altura;
    public boolean necesitaAtencionMedica;
}
