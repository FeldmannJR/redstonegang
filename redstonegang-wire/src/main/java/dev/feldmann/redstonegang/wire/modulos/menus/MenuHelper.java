package dev.feldmann.redstonegang.wire.modulos.menus;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    public static List<Integer> buildHollowSquare(int slotinicial, int slotfinal) {
        int rowinical = getRowAndColoum(slotinicial)[0];
        int lineinicial = getRowAndColoum(slotinicial)[1];
        int rowfinal = getRowAndColoum(slotfinal)[0];
        int linefinal = getRowAndColoum(slotfinal)[1];
        int minline = Math.min(linefinal, lineinicial);
        int maxline = Math.max(linefinal, lineinicial);
        int minrow = Math.min(rowinical, rowfinal);
        int maxrow = Math.max(rowinical, rowfinal);
        ArrayList<Integer> slots = new ArrayList();
        for (int x = minrow; x <= maxrow; x++) {
            for (int y = minline; y <= maxline; y++) {
                if (y == minline || y == maxline || x == minrow || x == maxrow) {
                    slots.add((x * 9) + y);
                }
            }
        }
        return slots;
    }

    public static List<Integer> buildSquare(int slotinicial, int slotfinal) {
        int rowinical = getRowAndColoum(slotinicial)[0];
        int lineinicial = getRowAndColoum(slotinicial)[1];
        int rowfinal = getRowAndColoum(slotfinal)[0];
        int linefinal = getRowAndColoum(slotfinal)[1];
        int minline = Math.min(linefinal, lineinicial);
        int maxline = Math.max(linefinal, lineinicial);
        int minrow = Math.min(rowinical, rowfinal);
        int maxrow = Math.max(rowinical, rowfinal);
        ArrayList<Integer> slots = new ArrayList();

        for (int x = minrow; x <= maxrow; x++) {
            for (int y = minline; y <= maxline; y++) {

                slots.add((x * 9) + y);
            }
        }
        return slots;
    }

    public static int[] getRowAndColoum(int slot) {
        int row = slot / 9;
        int coloum = slot % 9;
        return new int[]{
                row, coloum
        };
    }
}

