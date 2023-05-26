package com.example.lhti;

public class Stock {
    String login;
    double wklat;
    double zysk;
    String nazwa;
    double ilosc;

    String symbol;

    String data;

    public Stock(){

    }

    public Stock(String login, double wklat, double zysk, String nazwa, double ilosc,String symbol,String data) {
        this.login = login;
        this.wklat = wklat;
        this.zysk = zysk;
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.symbol = symbol;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String key) {
        this.data = data;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getWklat() {
        return wklat;
    }

    public void setWklat(double wklat) {
        this.wklat = wklat;
    }

    public double getZysk() {
        return zysk;
    }

    public void setZysk(double zysk) {
        this.zysk = zysk;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }
}
