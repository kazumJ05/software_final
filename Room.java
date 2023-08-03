/*
 * Room
 *
 * version 1.0
 *
 * 2023 08
 *
 * Copyright Kazumasa Ohara
 */

class Room {
    private int tatamiNum;
    private Tatami[] maked;
    private int[] VSCounting;
    private int[] HSCounting;
    private int[] idList;

    Room(int a) { // 部屋のコンストラクタ
        tatamiNum = a;
        maked = new Tatami[tatamiNum * 2 + 1];
        VSCounting = new int[tatamiNum * 2 + 1];
        HSCounting = new int[tatamiNum * 2 + 1];
        idList = new int[tatamiNum];
    }

    void makeTatami() {
        int k = this.determineLines();
        int[] pl;
        int directionSwitcher = 0;
        int direction = 0;
        int HS = 0;
        int VS = 1;
        int id = 0;
        int idCounter = 0;
        int tatamiCounter = 0;

        idList[idCounter] = id;
        idCounter++;

        for (int i = 0; i < 2 * k + 1; i++) {
            for (int j = 0; j < 2 * k + 1; j++) {
                pl = new int[] { j, i, 0, 0 };
                if (j == 0 && i != 0) {
                    VS = VSCounting[tatamiCounter - 1];
                    HS = HSCounting[tatamiCounter - 1];
                    direction = maked[tatamiCounter - 1].getDirection();
                    if (i == 1 || i == 2 * k) {
                        directionSwitcher = 0;
                    }

                }
                if (i < k) {
                    if (j != 0) {
                        if (directionSwitcher == 0) {
                            direction = 0;
                            VS = 1;
                            if ((i == 0 && tatamiCounter % 2 != 0) || (j != 0 && HSCounting[tatamiCounter - 1] == 0)) {
                                HS = 1;
                            } else if (HSCounting[tatamiCounter - 1] == 1
                                    && maked[tatamiCounter - 1].getDirection() == 0) {
                                HS = 0;
                            } else {
                                HS = 0;
                            }

                            if (j == 2 * k - 1 - i) {
                                directionSwitcher = 1;
                                HS = 1;
                                VS = 1;
                            }

                        } else {
                            if (j != 0) {
                                direction = 1;
                                HS = 1;

                                if (maked[tatamiCounter - 1].getDirection() == 0) {
                                    VS = 0;
                                }

                                if (j == i - 1) {
                                    directionSwitcher = 0;
                                    VS = 0;
                                }

                                if (tatamiCounter - (k * 2) - 1 >= 0) {
                                    if (VSCounting[tatamiCounter - (k * 2) - 1] == 1) {
                                        VS = 0;
                                        HS = 1;
                                    } else {
                                        VS = 1;
                                        HS = 1;
                                    }
                                }
                            }
                        }
                    }
                } else if (i == k) {
                    if (j != 0) {
                        HS = 1;
                        direction = 1;
                        if (VSCounting[tatamiCounter - 1] == 0 && j != 0) {
                            VS = 1;
                            if (j == k) {
                                VS = 2;
                            }
                        } else if (VSCounting[tatamiCounter - 1] == 2) {
                            if (k == 1) {
                                VS = 1;
                            } else {
                                VS = VSCounting[tatamiCounter - 3];
                            }

                        } else if (k == 1 && j == 2 * k) {
                            VS = 1;
                        } else {
                            VS = 0;
                        }
                    }
                } else if (i > k) {
                    if (j != 0) {
                        if (directionSwitcher == 1) {
                            direction = 1;
                            HS = 1;
                            if (maked[tatamiCounter - 1].getDirection() == 0 || (VSCounting[tatamiCounter - 1] == 0)) {
                                VS = 1;
                            } else {
                                VS = 0;
                            }

                            if (j == 2 * k - i) {
                                directionSwitcher = 0;
                            }

                        } else {
                            VS = 1;
                            direction = 0;
                            if (HSCounting[tatamiCounter - 1] == 0) {
                                HS = 1;
                            } else {
                                HS = 0;
                            }

                            if (j >= i && j != 2 * k) {
                                directionSwitcher = 1;
                            }
                        }
                    }
                }

                if (tatamiCounter > 0 && direction == 0 && maked[tatamiCounter - 1].getDirection() == 0
                        && HSCounting[tatamiCounter - 1] == 0) {
                    id = maked[tatamiCounter - 1].getID();
                } else if (tatamiCounter - (k * 2) - 1 >= 0 && direction == 1
                        && maked[tatamiCounter - (k * 2) - 1].getDirection() == 1
                        && VSCounting[tatamiCounter - (k * 2) - 1] == 0) {
                    id = maked[tatamiCounter - (k * 2) - 1].getID();
                } else if (i == k && j == k) {
                    id = 100;
                } else if (tatamiCounter == 0) {
                } else {
                    id++;
                    if (this.isUsed(id)) {
                        id = idList[idCounter - 1] + 1;
                        idList[idCounter] = id;
                        idCounter++;
                    } else {
                        idList[idCounter] = id;
                        idCounter++;
                    }
                }

                maked[tatamiCounter] = new Tatami(id, pl, direction);
                VSCounting[tatamiCounter] = VS;
                HSCounting[tatamiCounter] = HS;
                tatamiCounter++;
            }
        }
    }

    boolean isUsed(int a) {
        boolean current = false;
        for (int i = 0; i < idList.length; i++) {
            if (a == idList[i]) {
                current = true;
                break;
            }
        }
        return current;
    }

    boolean isSpreadable() {
        boolean ans = false;
        int i = 1;
        while (true) {
            int suuretu = 2 * i * i + 2 * i;
            if (suuretu == tatamiNum) {
                ans = true;
                break;
            } else if (suuretu > tatamiNum) {
                break;
            }
            i++;
        }
        return ans;
    }

    int determineLines() {
        int j = 1;
        while (true) {
            int suuretu = 2 * j * j + 2 * j;
            if (suuretu == tatamiNum) {
                break;
            }
            j++;
        }
        return j;
    }

    void tatamiDisplay() { // 表示
        String[] lines = { "******", "*", " ", "  ", "    ", "     *", "&" };
        int j = this.determineLines();
        int tatamiCount = 0;
        int middleCount = 0;
        int rowIn = 0;

        for (int i = 0; i < 4 * j + 3; i++) {
            if (i % 2 == 0) { // 文字以外の行
                if (i == 0 || i == 4 * j + 2) {
                    for (int k = 0; k < 2 * j + 1; k++) {
                        System.out.print(lines[0]);
                    }
                    System.out.print(lines[1]);
                } else {
                    for (int k = 0; k <= 2 * j + 1; k++) {
                        if (k == 0) {
                            System.out.print(lines[1]);
                        } else {
                            int changer = 5;

                            if (VSCounting[middleCount] == 1 || VSCounting[middleCount] == 2) {
                                changer = 0;

                            }

                            System.out.print(lines[changer]);
                            middleCount++;
                        }

                        if (k == 2 * j + 1) {
                            tatamiCount++;
                        }

                    }
                }
            } else { // 文字を含む行
                for (int k = 0; k <= 8 * j + 4; k++) {
                    if (k % 2 != 0) {
                        System.out.print(lines[3]);
                    } else {
                        if (k == 0 || k == 8 * j + 4) {
                            System.out.print(lines[1]);
                            rowIn = 0;
                        } else {
                            if (rowIn == 0) { // 文字
                                if (i == 2 * j + 1 && k == 4 * j + 2) {
                                    System.out.print(lines[6]);
                                } else {
                                    System.out.print(maked[tatamiCount].displayID());
                                }
                                rowIn++;
                            } else {// アスタリスク入るか空白

                                int changer = 2;

                                if (HSCounting[tatamiCount] == 1) {
                                    changer = 1;
                                }

                                System.out.print(lines[changer]);
                                rowIn = 0;
                                tatamiCount++;
                            }
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    void displayTatamiInfo() {
        Tatami[] connected = new Tatami[tatamiNum];
        Tatami[] sortCreate = new Tatami[tatamiNum * 2 + 1];
        int call = 0;

        for (int i = 0; i < tatamiNum * 2 + 1; i++) {
            sortCreate[i] = maked[i];
        }

        for (int i = 0; i < sortCreate.length - 1; i++) {
            for (int j = sortCreate.length - 1; j > i; j--) {
                if (sortCreate[j - 1].getID() > sortCreate[j].getID()) {
                    Tatami changer = sortCreate[j];
                    sortCreate[j] = sortCreate[j - 1];
                    sortCreate[j - 1] = changer;
                }
            }
        }

        for (int i = 0; i < tatamiNum; i++) {
            connected[i] = tatamiConnector(sortCreate[call], sortCreate[call + 1]);
            call += 2;
        }

        for (int i = 0; i < connected.length; i++) {
            System.out.println(connected[i].toString());
        }
    }

    Tatami tatamiConnector(Tatami a, Tatami b) {
        a.getPlace()[2] = b.getPlace()[0];
        a.getPlace()[3] = b.getPlace()[1];
        return a;
    }
}
