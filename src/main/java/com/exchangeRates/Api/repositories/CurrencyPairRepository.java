package com.exchangeRates.Api.repositories;

import com.exchangeRates.Api.entities.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair,Long> {

    @Query("SELECT cp FROM currency_pair cp WHERE cp.firstCurrency = :firstCurrency and cp.secondCurrency=:secondCurrency order by cp.date")
    List<CurrencyPair> getCurrencyPairsByNames(@Param("firstCurrency") String firstCurrency,
                                               @Param("secondCurrency")String secondCurrency);

    @Query("SELECT cp FROM currency_pair cp WHERE cp.date = :date order by cp.date")
    List<CurrencyPair> getCurrencyPairsByDate(@Param("date") LocalDate date);

    @Query("SELECT cp FROM currency_pair cp WHERE cp.date = :date and cp.firstCurrency = :firstCurrency and cp.secondCurrency = :secondCurrency order by cp.date")
    List<CurrencyPair> getCurrencyPairByDateAndName(@Param("date")LocalDate date,
                                                    @Param("firstCurrency")String firstCurrency,
                                                    @Param("secondCurrency")String secondCurrency);
}

