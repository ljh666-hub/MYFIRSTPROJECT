package com.ljh.DTO;

import java.util.List;

public class CountTotalDTO {
    private List<Integer> money;
    private List<String> date;

    @Override
    public String toString() {
        return "CountTotalDTO{" +
                "money=" + money +
                ", date=" + date +
                '}';
    }

    public List<Integer> getMoney() {
        return money;
    }

    public void setMoney(List<Integer> money) {
        this.money = money;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }
}
