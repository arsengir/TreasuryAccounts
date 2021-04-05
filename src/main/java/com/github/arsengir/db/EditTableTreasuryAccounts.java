package com.github.arsengir.db;

import com.github.arsengir.treasuryaccounts.TreasuryAccount;

import java.sql.*;
import java.util.List;

public class EditTableTreasuryAccounts {

    public static void InsertDataFromList(List<TreasuryAccount> accountList, String status){
        OraConnection.setCon("main");
        Connection conn = OraConnection.getConn();

        try {
            PreparedStatement pstmt =
                    conn.prepareStatement
                            ("insert into treasuryaccounts (ta, tsa, tbic, taholder, lsscheme, datedownload, status) " +
                                    " values(?, ?, ?, ?, ?, trunc(sysdate), ?)");

            for (TreasuryAccount treasuryAccount : accountList) {
                pstmt.setString( 1, treasuryAccount.getTA());
                pstmt.setString( 2, treasuryAccount.getTSA());
                pstmt.setString( 3, treasuryAccount.getTBIC());
                pstmt.setString( 4, treasuryAccount.getTAHolder());
                pstmt.setString( 5, treasuryAccount.getLSScheme());
                pstmt.setString( 6, status);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        OraConnection.closeConn();
        System.out.println( "done" );
    }
}
