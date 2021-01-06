package com.github.arsengir.treasuryaccounts;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class DownloadFileFromURL {

    public static final String URL_FILE = "https://www.roskazna.gov.ru/opendata/7710568760-TreasuryAccounts/meta.csv";

    public static String getUrlData() throws IOException {
        URLConnection connection = new URL(URL_FILE).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            if ("\"data\"".equals(split[0])) {
                reader.close();
                return split[1].replaceAll("^\"|\"$", "");
            }
        }
        reader.close();
        return null;
    }

    public static void downloadUsingStream(String urlStr) throws IOException {
        HashMap<String, TreasuryAccounts> hashMap = new HashMap<>();

        URLConnection connection = new URL("https://www.roskazna.gov.ru/upload/iblock/88a/data_20201225T0000_structure_20201225T0000.csv").openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            hashMap.put(split[0], new TreasuryAccounts(split[0],split[1],split[2],split[3],split[4],null));
        }
        reader.close();
        hashMap.forEach((k, v) -> System.out.println(k));
    }

}
