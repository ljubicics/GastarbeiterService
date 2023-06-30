package com.example.gastarbeiterservicefinal.drive.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Posao {
    public String klijent;
    private double cena;
    private Integer godina;
    private Integer mesec;
    private Integer dan;
    private String opisPosla;
    private double kurs;
    private NacinPlacanja nacinPlacanja;
    private Valuta valuta;
    private Prihodi prihodi;
    private double troskovi;

    public Posao(String klijent, double cena, int godina, int mesec, int dan, String opisPosla, double kurs, NacinPlacanja nacinPlacanja, double troskovi, Valuta valuta) {
        this.klijent = klijent;
        this.valuta = valuta;
        this.godina = godina;
        this.mesec = mesec;
        this.dan = dan;
        this.opisPosla = opisPosla;
        this.kurs = kurs;
        if(valuta == Valuta.DINAR) {
            this.cena = cena;
        } else {
            this.cena = cena * kurs;
        }
        this.nacinPlacanja = nacinPlacanja;
        this.troskovi = troskovi;
        this.prihodi = izracunajPrihode(godina, mesec, cena, valuta, kurs, troskovi);
    }

    public Prihodi izracunajPrihode(int godina, int mesec, double promet, Valuta valuta, double kurs, double troskovi) {
        // Da li se troskovi racunaju samo u dinarima? DAA
        if(valuta != Valuta.DINAR) {
            promet *= kurs;
        }
        // Uneti koliko se skida poreza koji se daje drzavi
        // Znaci samo dobit bez poreza.
        // Porez se placa na polovini meseca
        // Porez je ?
        // Uglavnom bice fiksna cifra za koju mozemo napraviti opciju da se skine od plusa
        // NPR OPIS TROSKA POREZ

        // TODO: Porez se unosi kao trosak

        return new Prihodi(godina, mesec, (promet - troskovi)*0.25, (promet - troskovi)*0.25, (promet - troskovi)*0.5);
    }

    public String getKlijent() {
        return klijent;
    }

    public void setKlijent(String klijent) {
        this.klijent = klijent;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
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

    public Integer getDan() {
        return dan;
    }

    public void setDan(Integer dan) {
        this.dan = dan;
    }

    public void setMesec(Integer mesec) {
        this.mesec = mesec;
    }

    public String getOpisPosla() {
        return opisPosla;
    }

    public void setOpisPosla(String opisPosla) {
        this.opisPosla = opisPosla;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public NacinPlacanja getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(NacinPlacanja nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public Prihodi getPrihodi() {
        return prihodi;
    }

    public void setPrihodi(Prihodi prihodi) {
        this.prihodi = prihodi;
    }

    public double getTroskovi() {
        return troskovi;
    }

    public void setTroskovi(double troskovi) {
        this.troskovi = troskovi;
    }
}
