package com.ljh.DTO;

import java.util.List;

public class CountRegisterDTO {
    private List<Integer> num;
    private List<String> registerTime;
    private List<Integer> total;

    @Override
    public String toString() {
        return "CountRegisterDTO{" +
                "num=" + num +
                ", registerTime=" + registerTime +
                ", total=" + total +
                '}';
    }

    public List<Integer> getNum() {
        return num;
    }

    public void setNum(List<Integer> num) {
        this.num = num;
    }

    public List<String> getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(List<String> registerTime) {
        this.registerTime = registerTime;
    }

    public List<Integer> getTotal() {
        return total;
    }

    public void setTotal(List<Integer> total) {
        this.total = total;
    }
}
