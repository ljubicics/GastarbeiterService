package com.example.gastarbeiterservicefinal.drive.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

public class Prihodi {

    private Integer godina;
    private Integer mesec;
    private double mihailo;
    private double zdravko;
    private double kasa;
    private NacinPlacanja nacinPlacanja;

    public Prihodi(int godina, int mesec, double mihailo, double nikola, double kasa) {
        this.godina = godina;
        this.mesec = mesec;
        this.mihailo = mihailo;
        this.zdravko = nikola;
        this.kasa = kasa;
    }
    public Prihodi(int godina, int mesec, double mihailo, double nikola, double kasa, NacinPlacanja nacinPlacanja) {
        this.godina = godina;
        this.mesec = mesec;
        this.mihailo = mihailo;
        this.zdravko = nikola;
        this.kasa = kasa;
        this.nacinPlacanja = nacinPlacanja;
    }

    public double getMihailo() {
        return mihailo;
    }

    public void setMihailo(double mihailo) {
        this.mihailo = mihailo;
    }

    public double getZdravko() {
        return zdravko;
    }

    public void setZdravko(double zdravko) {
        this.zdravko = zdravko;
    }

    public double getKasa() {
        return kasa;
    }

    public void setKasa(double kasa) {
        this.kasa = kasa;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public Integer getMesec() {
        return mesec;
    }

    public void setMesec(Integer mesec) {
        this.mesec = mesec;
    }

    public NacinPlacanja getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(NacinPlacanja nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }
}
