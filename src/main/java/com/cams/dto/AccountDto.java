package com.cams.dto;

public class AccountDto {
    private Integer id;
    private String name;
    private Double balance;

    public AccountDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountDto(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public AccountDto(Integer id, String name, Double balance) {
        this.balance = balance;
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
