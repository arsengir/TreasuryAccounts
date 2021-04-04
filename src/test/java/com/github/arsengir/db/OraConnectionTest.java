package com.github.arsengir.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OraConnectionTest {

    @Test
    void setCon() {
        OraConnection.setCon("main");
        assertTrue(true);
    }
}