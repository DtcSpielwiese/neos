package de.msg.gbn.dtc.neos.db;

public enum Tariftyp {
    BASE,
    DIV,
    HAM,
    HMO;

    public static Tariftyp fromString(final String s) {
        return Tariftyp.valueOf(s);
    }
}

