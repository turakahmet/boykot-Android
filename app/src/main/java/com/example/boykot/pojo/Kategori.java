package com.example.boykot.pojo;

public enum Kategori {
    HEPSI("Hepsi"),
    GIDA("Gıda"),
    ICECEK("İçecek"),
    TEMIZLIK("Temizlik"),
    KOZMETIK("Kozmetik"),
    MEDYA("Medya"),
    TEKNOLOJI("Teknoloji"),
    SIGARA("Sigara"),
    AKARYAKIT("Akaryakıt"),
    ILAC("İlaç"),
    DIGER("Diğer");


    public String etiket;
    Kategori(String etiket) {
        this.etiket = etiket;
    }
    public String getEtiket() {
        return etiket;
    }
}
