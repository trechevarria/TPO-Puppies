package application.dto.animal;

import domain.enums.TipoAnimal;

public class AnimalUpdateDTO {
    public TipoAnimal tipo;
    public double peso;
    public double altura;
    public boolean necesitaAtencionMedica;
}
