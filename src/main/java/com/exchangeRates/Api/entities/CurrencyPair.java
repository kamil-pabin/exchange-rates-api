package com.exchangeRates.Api.entities;


import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "currency_pair")
public class CurrencyPair {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "date",
            nullable = false
    )
    private LocalDate date;
    @Column(
            name = "first_currency",
            nullable = false,
            length = 3
    )
    private String firstCurrency;
    @Column(
            name = "second_currency",
            nullable = false,
            length = 3
    )
    private String secondCurrency;
    @Column(
            name = "exchange_rate",
            nullable = false
    )
    private double exchangeRate;

    public CurrencyPair(String firstCurrency, String secondCurrency, double exchangeRate, LocalDate date){
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
        this.exchangeRate = exchangeRate;
        this.date = date;
    }
    public String getFirstCurrency(){
        return firstCurrency;
    }
    public String getSecondCurrency(){
        return secondCurrency;
    }
    public LocalDate getDate(){
        return date;
    }
    public double getExchangeRate(){
        return exchangeRate;
    }

    @Override
    public String toString() {
        return date +","+firstCurrency+","+secondCurrency+","+exchangeRate;
    }

    public CurrencyPair() {

    }
}