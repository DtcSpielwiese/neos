package de.msg.gbn.dtc.neos.db;

public enum Altersklasse {
    ERW,
    JUG,
    KIN;

    public static Altersklasse fromString(final String s) {
        return Altersklasse.valueOf(s);
    }
}

