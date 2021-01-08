package com.github.arsengir.treasuryaccounts;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashSet;

public class TreasuryAccountsUtil {

    public static final String URL_FILE = "https://www.roskazna.gov.ru/opendata/7710568760-TreasuryAccounts/meta.csv";

    private static LinkedHashSet<TreasuryAccount> treasuryAccountsSet  = new LinkedHashSet<>();

    public static void init() {
        if (treasuryAccountsSet.isEmpty()) {
            try {
                treasuryAccountsSet = getSetTreasuryAccountsFromLink(getUrlData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получение ссылки на файл со списком Казначейских счетов
     *
     * @return ссылка на файл со счетами
     * @throws IOException
     */
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
                return split[1].replaceAll("^\"|\"$", "").replaceAll("http://", "https://");
            }
        }
        reader.close();
        return null;
    }

    /**
     * Получение списка (TreasuryAccountsSet) счетов по ссылке
     *
     * @param urlStr ссылка на файл со счетами
     * @throws IOException
     */
    public static LinkedHashSet<TreasuryAccount> getSetTreasuryAccountsFromLink(String urlStr) throws IOException {
        LinkedHashSet<TreasuryAccount> treasuryAccountsSet = new LinkedHashSet<>();

        URLConnection connection = new URL(urlStr).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        line = reader.readLine();
        if ("TA,TSA,TBIC,TAOpenDate,TAHolder,LSScheme".equals(line)) {
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");

                String ta = split[0];
                String tsa = split[1];
                String tbic = split[2];
                String taOpenDate = split[3];
                String taHolder = null;
                String lsScheme = null;

                if (split.length > 4) {
                    int endIndex = split.length - 1;
                    if ("LS1".equals(split[endIndex]) || "LS2".equals(split[endIndex])) {
                        lsScheme = split[endIndex];
                        endIndex--;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 4; i <= endIndex; i++) {
                        sb.append(split[i]);
                        if (i != endIndex) {
                            sb.append(",");
                        }
                    }
                    taHolder = sb.toString();
                }
                treasuryAccountsSet.add(new TreasuryAccount(ta, tsa, tbic, taOpenDate, taHolder, lsScheme));
            }
            reader.close();
            System.out.println(treasuryAccountsSet.size());
        }
        return treasuryAccountsSet;
    }

    /**
     * Сохранение списка в файл на диск
     * @param fileName - имя файла
     */
    public static void saveToFile(String fileName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(fileName);
        for (TreasuryAccount ta: treasuryAccountsSet) {
            outputStream.write(ta.getBytesForSaveToFile());
        }
        outputStream.close();
    }

}
