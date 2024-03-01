package com.example.boykot.pojo;

public enum Ulke {
    TR("Türkiye"),
    EN("İngiltere"),
    ABD("Amerika Birleşik Devleti"),
    ;

    public String etiket;
    Ulke(String etiket) {
        this.etiket = etiket;
    }
    public String getEtiket() {
        return etiket;
    }
}
