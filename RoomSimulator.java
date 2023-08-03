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
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int tatami = 0;

        System.out.print("部屋の大きさは何畳半:");
        tatami = stdIn.nextInt();
    }
}
