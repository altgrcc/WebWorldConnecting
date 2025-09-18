package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cat")
public class CatFactsController {

    final String API_URL = "https://catfact.ninja/fact";
    final String translateAPI = "http://10.34.56.141:5540/translate";

    @GetMapping("/fact")
    public String getFact() {

        RestTemplate restTemplate = new RestTemplate();
        CatFact fact = restTemplate.getForObject(API_URL, CatFact.class);
        return fact.getFact();
    }

    @GetMapping("/limited-fact/{length}")
    public String getLimitedFact(@PathVariable int length) {

        RestTemplate restTemplate = new RestTemplate();
        String urlWithParams = API_URL + "?max_length=" + length; // se parsea el json para no regresar el parametro
        CatFact fact = restTemplate.getForObject(urlWithParams, CatFact.class);
        return fact.getFact();

    }

    @GetMapping("/translated-fact")
    public String getTranslatedFact() {

        RestTemplate restTemplate = new RestTemplate();
        CatFact fact = restTemplate.getForObject(API_URL, CatFact.class);

        TranslateRequest request = new TranslateRequest();
        request.setQ(fact.getFact());
        request.setSource("en");
        request.setTarget("es");
        request.setFormat("text");

        TranslateRequest translation = restTemplate.postForObject(translateAPI, request, TranslateRequest.class);
        return translation.getTranslatedText();
    }

}
