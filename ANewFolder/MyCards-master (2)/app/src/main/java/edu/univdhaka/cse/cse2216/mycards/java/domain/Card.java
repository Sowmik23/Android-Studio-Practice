package edu.univdhaka.cse.cse2216.mycards.java.domain;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private int id;
    private String type;
    private String bankName;
    private String number;
    private String cardholderName;
    private String expiryDate;
    private int cvv;

    public Card() {
        // No-argument constructor required for deserialization by Firebase
    }

    public Card(String type, String bankName, String number, String cardholderName, String expiryDate, int cvv, int id) {
        this.type = type;
        this.bankName = bankName;
        this.number = number;
        this.cardholderName = cardholderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.id = id;
    }

    public Card(String type, String bankName, String number, String cardholderName, String expiryDate, int cvv) {
        this(type, bankName, number, cardholderName, expiryDate, cvv, 0);
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                cvv == card.cvv &&
                Objects.equals(type, card.type) &&
                Objects.equals(bankName, card.bankName) &&
                Objects.equals(number, card.number) &&
                Objects.equals(cardholderName, card.cardholderName) &&
                Objects.equals(expiryDate, card.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, bankName, number, cardholderName, expiryDate, cvv);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", bankName='" + bankName + '\'' +
                ", number='" + number + '\'' +
                ", cardholderName='" + cardholderName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv=" + cvv +
                '}';
    }
}
