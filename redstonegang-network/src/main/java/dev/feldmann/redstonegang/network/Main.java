package dev.feldmann.redstonegang.network;


import dev.feldmann.redstonegang.app.RedstoneTerminal;

public class Main {


    public static void main(String[] args) {
        RedstoneTerminal.start(new RedstoneNetwork(),args);
    }
}
