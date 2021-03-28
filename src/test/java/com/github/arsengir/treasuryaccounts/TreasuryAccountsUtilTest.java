package com.github.arsengir.treasuryaccounts;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

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
        LinkedHashSet<TreasuryAccount> treasuryAccountsSet  = new LinkedHashSet<>();
        try {
            System.out.println(TreasuryAccountsUtil.getUrlData());
            treasuryAccountsSet = TreasuryAccountsUtil.getSetTreasuryAccountsFromLink(TreasuryAccountsUtil.getUrlData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(treasuryAccountsSet.size());
        assertFalse(treasuryAccountsSet.isEmpty());
    }

    @Test
    public void saveToFile() {
        TreasuryAccountsUtil.init();
        String fileName = "C:\\Users\\Arsen\\Desktop\\1.txt";
        try {
            TreasuryAccountsUtil.saveToFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(new File(fileName).exists());
    }

    @Test
    public void saveToFilesDifferenceBetween2Set() {
        TreasuryAccountsUtil.init();
        String fileNameAdd = "C:\\Users\\Arsen\\Desktop\\2.txt";
        String fileNameDel = "C:\\Users\\Arsen\\Desktop\\3.txt";
        String urlCompareSet = "https://www.roskazna.gov.ru/upload/iblock/fd8/data_20201225T0000_structure_20201225T0000.csv";
        try {
            TreasuryAccountsUtil.saveToFilesDifferenceBetween2Set(fileNameAdd, fileNameDel, urlCompareSet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(new File(fileNameAdd).exists());
        assertTrue(new File(fileNameDel).exists());
    }

}