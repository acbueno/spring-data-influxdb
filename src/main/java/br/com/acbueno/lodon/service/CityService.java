package br.com.acbueno.lodon.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CityService {

  public List<String> getCityOfRmcSaoPaulo() {
    return Arrays.asList("Americana", "Artur Nogueira", "Campinas", "Cosmópolis",
        "Engenheiro Coelho", "Holambra", "Hortolândia", "Indaiatuba", "Itatiba", "Jaguariúna",
        "Monte Mor", "Nova Odessa", "Paulínia", "Pedreira", "Santa Bárbara d'Oeste",
        "Santo Antônio de Posse", "Sumaré", "Valinhos", "Vinhedo");
  }

}
