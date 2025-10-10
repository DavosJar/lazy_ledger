package com.lazyledger.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LazyLedgerController {

    @GetMapping("/")
    public String hello() {
        return "ada ";
    }
}