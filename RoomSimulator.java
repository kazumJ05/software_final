
/*
 * RoomSimulator
 *
 * version 1.0
 *
 * 2023 08
 *
 * Copyright Kazumasa Ohara
 */
import java.util.Scanner;

public class RoomSimulator {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);
        int tatami = 0;

        System.out.print("部屋の大きさは何畳半:");
        tatami = stdIn.nextInt();

        Room room = new Room(tatami);

        if (room.isSpreadable()) {
            room.makeTatami();
            System.out.println("畳の情報:");
            room.displayTatamiInfo();
            System.out.println("部屋の図は:");
            room.tatamiDisplay();
        } else {
            System.out.println("畳を正方形に敷くことができません。");
        }
    }
}
