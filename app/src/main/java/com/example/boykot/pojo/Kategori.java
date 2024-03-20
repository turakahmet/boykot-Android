package com.example.boykot.pojo;

import android.content.Context;

import com.example.boykot.R;

public enum Kategori {
    HEPSI(R.string.HEPSI),
    GIDA(R.string.GIDA),
    ICECEK(R.string.ICECEK),
    TEMIZLIK(R.string.TEMIZLIK),
    KOZMETIK(R.string.KOZMETIK),
    MEDYA(R.string.MEDYA),
    TEKNOLOJI(R.string.TEKNOLOJI),
    SIGARA(R.string.SIGARA),
    AKARYAKIT(R.string.AKARYAKIT),
    ILAC(R.string.ILAC),
    DIGER(R.string.DIGER);

    private int stringResourceID;

    Kategori(int stringResourceID) {
        this.stringResourceID = stringResourceID;
    }

    public String getEtiket(Context context) {
        return context.getString(stringResourceID);
    }
}
