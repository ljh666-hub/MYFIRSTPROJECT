package com.ljh.DTO;

import java.util.List;

public class FindUncancelDTO {
    private List<Integer> orderAmount;
    private List<String> date;
    private List<Integer> allOrders;

    @Override
    public String toString() {
        return "FindUncancelDTO{" +
                "orderAmount=" + orderAmount +
                ", date=" + date +
                ", allOrders=" + allOrders +
                '}';
    }

    public List<Integer> getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(List<Integer> orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Integer> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<Integer> allOrders) {
        this.allOrders = allOrders;
    }
}
