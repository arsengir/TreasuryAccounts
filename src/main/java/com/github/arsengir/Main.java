package com.github.arsengir;

import com.github.arsengir.treasuryaccounts.TreasuryAccountsUtil;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String MENU = "Выберете действие: \n" +
            "1. Сравнить 2 файла \n" +
            "2. Добавить в таблицу сравнение 2 файлов \n" +
            "0. Выход \n";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        whileExit:
        while (true) {
            System.out.println(MENU);
            int menu = Integer.parseInt(scanner.nextLine());
            String fileNameOld;
            String fileNameNew;
            switch (menu) {
                case 1:
                    System.out.print("Введите путь до файла 1:");
                    fileNameOld = scanner.nextLine();
                    System.out.print("Введите путь до файла 2:");
                    fileNameNew = scanner.nextLine();
                    TreasuryAccountsUtil.saveToFilesDifferenceBetween2List("Add.csv", "Del.csv",
                            fileNameOld, fileNameNew);
                    break;
                case 2:
                    System.out.print("Введите путь до файла 1:");
                    fileNameOld = scanner.nextLine();
                    System.out.print("Введите путь до файла 2:");
                    fileNameNew = scanner.nextLine();
                    TreasuryAccountsUtil.saveToDBDifferenceBetween2List(fileNameOld, fileNameNew);
                    break;
                case 0:
                    break whileExit;
            }
        }
        scanner.close();
    }
}
