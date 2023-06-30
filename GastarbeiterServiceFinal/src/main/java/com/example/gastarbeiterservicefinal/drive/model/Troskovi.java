package com.example.gastarbeiterservicefinal.drive.model;

import java.util.Date;

public class Troskovi {
    private double suma;
    private Integer mesec;
    private Integer godina;
    private Integer dan;
    private String kljucnaRec;
    private String opis;


    public Troskovi(double suma, Integer mesec, Integer godina, Integer dan, String kljucnaRec, String opis) {
        this.suma = suma;
        this.mesec = mesec;
        this.godina = godina;
        this.dan = dan;
        this.kljucnaRec = kljucnaRec;
        this.opis = opis;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Integer getMesec() {
        return mesec;
    }

    public void setMesec(Integer mesec) {
        this.mesec = mesec;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public Integer getDan() {
        return dan;
    }

    public void setDan(Integer dan) {
        this.dan = dan;
    }

    public String getKljucnaRec() {
        return kljucnaRec;
    }

    public void setKljucnaRec(String kljucnaRec) {
        this.kljucnaRec = kljucnaRec;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
