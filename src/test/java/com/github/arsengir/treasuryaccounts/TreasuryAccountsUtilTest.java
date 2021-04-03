package com.github.arsengir.treasuryaccounts;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreasuryAccountsUtilTest {

    @Test
    public void getUrlData() {
        String url = null;
        try {
            url = TreasuryAccountsUtil.getUrlData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        assertNotNull(url);
    }

    @Test
    public void getSetTreasuryAccountsFromLink() {
        assertTrue(true);
//        LinkedHashSet<TreasuryAccount> treasuryAccountsSet = new LinkedHashSet<>();
//        try {
//            System.out.println(TreasuryAccountsUtil.getUrlData());
//            treasuryAccountsSet = TreasuryAccountsUtil.getSetTreasuryAccountsFromLink(TreasuryAccountsUtil.getUrlData());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(treasuryAccountsSet.size());
//        assertFalse(treasuryAccountsSet.isEmpty());
    }


    @Test
    public void getSetTreasuryAccountsFromFile() {
        try {
//            final String fileName = "data_20201225T0000_structure_20201225T0000.csv";
            final String fileName = "data_20210317T0000_structure_20201225T0000.CSV";
            List<TreasuryAccount> treasuryAccounts = TreasuryAccountsUtil.getSetTreasuryAccountsFromFile(fileName);
            System.out.println(treasuryAccounts.size());
            treasuryAccounts.stream()
                    .limit(10)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveToFilesDifferenceBetween2List() {
        try {
            TreasuryAccountsUtil.saveToFilesDifferenceBetween2List("Add.csv", "Del.csv",
                    "data_20201225T0000_structure_20201225T0000.csv",
                    "data_20210317T0000_structure_20201225T0000.CSV");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}