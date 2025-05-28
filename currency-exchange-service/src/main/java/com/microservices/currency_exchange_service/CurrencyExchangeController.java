package com.microservices.currency_exchange_service;

import com.microservices.currency_exchange_service.jpa.CurrencyExchangeRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeRepository currencyExchangeRepository;
    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.environment = environment;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if (currencyExchange == null) throw new RuntimeException("Unable to find data for " + from + " to "+ to);
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
