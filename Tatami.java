/*
 * Tatami
 *
 * version 1.0
 *
 * 2023 08
 *
 * Copyright Kazumasa Ohara
 */

class Tatami {
    private int id;
    private int[] place;
    private int direction;

    Tatami(int id, int[] place, int dir) {
        this.id = id;
        this.place = place;
        this.direction = dir;
    }

    String displayID() {
        return String.format("%S", (char) ('A' + id));
    }

    public String toString() {
        return String.format("畳%S: 座標/(%d, %d),(%d, %d) 方向/%d", (char) ('A' + id), place[0], place[1], place[2],
                place[3], direction);
    }

    int getID() {
        return id;
    }

    int[] getPlace() {
        return place;
    }

    int getDirection() {
        return direction;
    }
}
