package com.ljh.DTO;

public class BrandSelectDTO {
    private String brandSell;
    private int brandPriceBegin;
    private int brandPriceEnd;

    @Override
    public String toString() {
        return "BrandSelectDTO{" +
                "brandSell='" + brandSell + '\'' +
                ", brandPriceBegin=" + brandPriceBegin +
                ", brandPriceEnd=" + brandPriceEnd +
                '}';
    }

    public int getBrandPriceBegin() {
        return brandPriceBegin;
    }

    public void setBrandPriceBegin(int brandPriceBegin) {
        this.brandPriceBegin = brandPriceBegin;
    }

    public int getBrandPriceEnd() {
        return brandPriceEnd;
    }

    public void setBrandPriceEnd(int brandPriceEnd) {
        this.brandPriceEnd = brandPriceEnd;
    }

    public String getBrandSell() {
        return brandSell;
    }

    public void setBrandSell(String brandSell) {
        this.brandSell = brandSell;
    }
}
