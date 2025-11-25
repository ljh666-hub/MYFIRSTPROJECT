package com.ljh.DTO;

import java.util.List;

public class SellsTopDTO {
    private List<String> brandName;
    private List<Integer> brandSell;

    @Override
    public String toString() {
        return "SellsTopDTO{" +
                "brandName=" + brandName +
                ", brandSell=" + brandSell +
                '}';
    }

    public List<String> getBrandName() {
        return brandName;
    }

    public void setBrandName(List<String> brandName) {
        this.brandName = brandName;
    }

    public List<Integer> getBrandSell() {
        return brandSell;
    }

    public void setBrandSell(List<Integer> brandSell) {
        this.brandSell = brandSell;
    }
}
