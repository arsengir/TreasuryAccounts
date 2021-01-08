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
            treasuryAccountsSet = TreasuryAccountsUtil.getSetTreasuryAccountsFromLink(TreasuryAccountsUtil.getUrlData());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}