package com.example.boykot.pojo;


public class BoykotMarka {
    private String urunAdi;
    private String urunAciklamasi;
    private Ulke ulke;
    private String barkodNo;
    private UrunTip urunTip;
    private String urunLogo;
    private boolean expanded =false;
    public String getUrunAdi() {
        return urunAdi;
    }

    public BoykotMarka() {
    }

    public BoykotMarka(String urunAdi, String urunAciklamasi, Ulke ulke, String barkodNo, UrunTip urunTip) {
        this.urunAdi = urunAdi;
        this.urunAciklamasi = urunAciklamasi;
        this.ulke = ulke;
        this.barkodNo = barkodNo;
        this.urunTip = urunTip;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public String getUrunAciklamasi() {
        return urunAciklamasi;
    }

    public void setUrunAciklamasi(String urunAciklamasi) {
        this.urunAciklamasi = urunAciklamasi;
    }

    public Ulke getUlke() {
        return ulke;
    }

    public void setUlke(Ulke ulke) {
        this.ulke = ulke;
    }

    public String getBarkodNo() {
        return barkodNo;
    }

    public void setBarkodNo(String barkodNo) {
        this.barkodNo = barkodNo;
    }

    public UrunTip getUrunTip() {
        return urunTip;
    }

    public void setUrunTip(UrunTip urunTip) {
        this.urunTip = urunTip;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getUrunLogo() {
        return urunLogo;
    }

    public void setUrunLogo(String urunLogo) {
        this.urunLogo = urunLogo;
    }
}
