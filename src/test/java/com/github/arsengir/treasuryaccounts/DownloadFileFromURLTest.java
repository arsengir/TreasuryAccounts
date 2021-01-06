package com.github.arsengir.treasuryaccounts;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DownloadFileFromURLTest {

    private final DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL();

    @Test
    public void getUrlData() {
        String url = null;
        try {
            url = downloadFileFromURL.getUrlData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        assertNotNull(url);
    }

    @Test
    public void downloadUsingStream() {
        try {
            downloadFileFromURL.downloadUsingStream(downloadFileFromURL.getUrlData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}