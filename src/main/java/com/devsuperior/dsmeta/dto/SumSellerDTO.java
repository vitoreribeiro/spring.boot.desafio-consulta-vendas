package com.devsuperior.dsmeta.dto;

public class SumSellerDTO {

    private String name;
    private Double amount;

    public SumSellerDTO(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }
}
