package com.github.arsengir.treasuryaccounts;


import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class TreasuryAccountsUtil {

    public static final String URL_FILE = "https://www.roskazna.gov.ru/opendata/7710568760-TreasuryAccounts/meta.csv";

    private static LinkedHashSet<TreasuryAccount> treasuryAccountsSet = new LinkedHashSet<>();

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
     * @throws IOException ошибка
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
     * @return set со списком счетов
     * @throws IOException ошибка
     */
    public static LinkedHashSet<TreasuryAccount> getSetTreasuryAccountsFromLink(String urlStr) throws IOException {
        LinkedHashSet<TreasuryAccount> treasuryAccountsSet;

        URLConnection connection = new URL(urlStr).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();

        try (BOMInputStream bomIn = new BOMInputStream(connection.getInputStream());
             InputStreamReader in = new InputStreamReader(bomIn);
             BufferedReader reader = new BufferedReader(in)) {

            List<TreasuryAccount> list = parseCSV(reader);
            treasuryAccountsSet = new LinkedHashSet<>(list);
        }

        return treasuryAccountsSet;
    }

    /**
     * Получение списка счетов из файла
     *
     * @param fileName путь до файла
     * @return List TreasuryAccount
     * @throws IOException ошибка
     */
    public static List<TreasuryAccount> getSetTreasuryAccountsFromFile(String fileName) throws IOException {
        List<TreasuryAccount> list;
        try (FileInputStream fis = new FileInputStream(fileName);
             BOMInputStream bomIn = new BOMInputStream(fis);
             BufferedReader reader = new BufferedReader(new InputStreamReader(bomIn))) {
            list = parseCSV(reader);
        }
        list = list.stream()
                .sorted(Comparator.comparing(TreasuryAccount::getTA))
                .collect(Collectors.toList());

        return list;
    }

    private static List<TreasuryAccount> parseCSV(Reader reader) {
        return (List<TreasuryAccount>) new CsvToBeanBuilder(reader)
                .withType(TreasuryAccount.class)
                .build()
                .parse();
    }

    /**
     * Сохранение разницы между 2 списками в файл на диск
     *
     * @param fileNameAdd - имя файла счета которые добавлены или изменены в новом файле
     * @param fileNameDel - имя файла счета которые удалены
     * @param fileNameOld - имя старого файла
     * @param fileNameNew - имя нового файла
     * @throws IOException ошибка
     */
    public static void saveToFilesDifferenceBetween2List(String fileNameAdd, String fileNameDel,
                                                         String fileNameOld, String fileNameNew) throws IOException {
        List<TreasuryAccount> listOld = TreasuryAccountsUtil.getSetTreasuryAccountsFromFile(fileNameOld);
        List<TreasuryAccount> listNew = TreasuryAccountsUtil.getSetTreasuryAccountsFromFile(fileNameNew);

        // счета которые добавлены или изменены в новом файле
        Set<TreasuryAccount> setOld = new HashSet<>(listOld);
        List<TreasuryAccount> accountModify = listNew.stream()
                .filter(account -> !setOld.contains(account))
                .collect(Collectors.toList());

        System.out.println(accountModify.size());
        accountModify.stream()
                .limit(10)
                .forEach(System.out::println);

        formingCsvFileFromList(fileNameAdd, accountModify);

        // счета которые удалены в новом файле
        Set<String> setNew = listNew.stream()
                .map(TreasuryAccount::getTA)
                .collect(Collectors.toSet());
        List<TreasuryAccount> accountDel = listOld.stream()
                .filter(account -> !setNew.contains(account.getTA()))
                .collect(Collectors.toList());

        System.out.println(accountDel.size());
        accountDel.stream()
                .limit(10)
                .forEach(System.out::println);

        formingCsvFileFromList(fileNameDel, accountDel);
    }

    private static void formingCsvFileFromList(String fileName, List<TreasuryAccount> accounts) throws IOException {
        try(FileOutputStream outputStream = new FileOutputStream(fileName)) {
            for (TreasuryAccount account : accounts) {
                outputStream.write(account.getBytesForSaveToFile());
                outputStream.flush();
            }
        }
    }

}
