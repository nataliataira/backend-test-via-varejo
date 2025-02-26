package com.via_varejo.backend_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class InterestRateApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Double getAccumulatedSelic() throws RuntimeException{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -31);
        String start = DATE_FORMAT.format(calendar.getTime());

        calendar.add(Calendar.DATE, 30);
        String endDate = DATE_FORMAT.format(calendar.getTime());

        String API_URL = String.format("https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json&dataInicial=%s&dataFinal=%s", start, endDate);
        System.out.println(API_URL);
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> selicRates = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

            double accumulatedFactor = 1.0;
            for (Map<String, String> rateEntry : selicRates) {
                double dailyRate = Double.parseDouble(rateEntry.get("valor")) / 100.0;
                accumulatedFactor *= (1 + dailyRate);
            }
            return (accumulatedFactor - 1) * 100.0;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar os dados da API do Banco Central.");
        }
    }
}
