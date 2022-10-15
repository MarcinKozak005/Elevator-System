package org.example.utils;

public class Triplet<X, Y, Z> {
    X fst;
    Y snd;
    Z trd;

    public Triplet(X fst, Y snd, Z trd) {
        this.fst = fst;
        this.snd = snd;
        this.trd = trd;
    }

    @Override
    public String toString() {
        return "<" + fst + ',' + snd + ',' + trd + '>';
    }
}
