package com.example.boykot.pojo;

public enum UrunTip {
    GIDA("GIDA"),
    ICECEK("ICECEK"),
    TEMIZLIK("TEMIZLIK"),
    KOZMETIK("KOZMETIK"),
    MEDYA("MEDYA"),
    TEKNOLOJI("TEKNOLOJI"),
    SIGARA("SIGARA"),
    AKARYAKIT("AKARYAKIT"),
    ILAC("ILAC"),
    DIGER("DIGER"),
            ;

    public String etiket;
    UrunTip(String etiket) {
        this.etiket = etiket;
    }
    public String getEtiket() {
        return etiket;
    }
}
