package com.github.arsengir.treasuryaccounts;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Objects;

public class TreasuryAccount {
    // Номер казначейского счета
    @CsvBindByName
    private String TA;
    // Номер единого казначейского счета
    @CsvBindByName
    private String TSA;
    // Банковский идентификационный код территориального органа Федерального казначейства
    // в платежной системе Банка России2
    @CsvBindByName
    private String TBIC;
    //Дата открытия казначейского счета
    @CsvDate(value = "dd.MM.yyyy")
    @CsvBindByName
    private LocalDate TAOpenDate;
    //Наименование владельца казначейского счета
    @CsvBindByName
    private String TAHolder;
    //Вариант открытия лицевого счета (LS1 - лицевые счета открыты в территориальном органе Федерального
    //казначейства, LS2 - лицевые счета открыты в финансовом органе или органе управления внебюджетным фондом)
    @CsvBindByName
    private String LSScheme;

    public TreasuryAccount() {
        // Пустой конструктор
    }

    public TreasuryAccount(String ta, String tsa, String tbic, LocalDate taOpenDate, String taHolder, String lsScheme) {
        TA = ta;
        TSA = tsa;
        TBIC = tbic;
        TAOpenDate = taOpenDate;
        TAHolder = taHolder;
        LSScheme = lsScheme;
    }

    @Override
    public String toString() {
        return "TreasuryAccount{" +
                "TA='" + TA + '\'' +
                ", TSA='" + TSA + '\'' +
                ", TBIC='" + TBIC + '\'' +
                ", TAOpenDate='" + TAOpenDate + '\'' +
                ", TAHolder='" + TAHolder + '\'' +
                ", LSScheme='" + LSScheme + '\'' +
                '}';
    }

    public byte[] getBytesForSaveToFile() {
        String line = TA + "," + TSA + "," + TBIC + "," + TAOpenDate + "," + TAHolder + "," + LSScheme + "\n";
        return line.getBytes(StandardCharsets.UTF_8);
    }

    public String getTA() {
        return TA;
    }

    public String getTSA() {
        return TSA;
    }

    public String getTBIC() {
        return TBIC;
    }

    public LocalDate getTAOpenDate() {
        return TAOpenDate;
    }

    public String getTAHolder() {
        return TAHolder;
    }

    public String getLSScheme() {
        return LSScheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreasuryAccount that = (TreasuryAccount) o;
        return Objects.equals(TA, that.TA) && Objects.equals(TSA, that.TSA) && Objects.equals(TBIC, that.TBIC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TA, TSA, TBIC);
    }
}
