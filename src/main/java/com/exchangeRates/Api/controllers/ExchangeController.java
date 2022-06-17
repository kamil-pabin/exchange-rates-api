package com.exchangeRates.Api.controllers;

import com.exchangeRates.Api.entities.CurrencyPair;
import com.exchangeRates.Api.repositories.CurrencyPairRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ExchangeController {

    private CurrencyPairRepository currencyPairRepository;

    public ExchangeController(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll() {
        List<CurrencyPair> dataFromRepo = currencyPairRepository.findAll();
        return new ResponseEntity<>(dataFromRepo, HttpStatus.OK);
    }

    @GetMapping("/by-name/{first}/{second}")
    public ResponseEntity getSelectedPairs(@PathVariable("first") String firstCurrency,
                                           @PathVariable("second") String secondCurrency) {
        List<CurrencyPair> dataFromRepo = currencyPairRepository.getCurrencyPairsByNames(firstCurrency, secondCurrency);

        if (dataFromRepo.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(dataFromRepo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That currency pair does not exist");
        }
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity getByDate(@PathVariable("date") String date) {
        List<CurrencyPair> dataFromRepo;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            dataFromRepo = currencyPairRepository.getCurrencyPairsByDate(LocalDate.parse(date, dtf));
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only dd-mm-yyyy date format is supported");
        }

        if (dataFromRepo.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(dataFromRepo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data for this day");
        }
    }
    @GetMapping("/by-date-and-name/{date}/{firstName}/{secondName}")
    public ResponseEntity getByNameAndDate(@PathVariable("date")String date,
                                           @PathVariable("firstName")String firstName,
                                           @PathVariable("secondName")String secondName){
        List<CurrencyPair> dataFromRepo;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            dataFromRepo = currencyPairRepository.getCurrencyPairByDateAndName(LocalDate.parse(date, dtf),firstName,secondName);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only dd-mm-yyyy date format is supported");
        }
        if (dataFromRepo.size()==1){
            return ResponseEntity.status(HttpStatus.OK).body(dataFromRepo);
        }
        if (currencyPairRepository.getCurrencyPairsByNames(firstName,secondName).size()==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("That currency pair does not exist");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data for this day");

    }
}