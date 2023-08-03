/*
 * Room
 *
 * version 1.0
 *
 * 2023 08
 *
 * Copyright Kazumasa Ohara
 */

 class Room{
    private int tatamiNum;
    private Tatami[] maked;
    private int[] VSCounting;
    private int[] HSCounting;
    private int[] idList;

    Room(int a){ //部屋のコンストラクタ
        tatamiNum = a;
        maked = new Tatami[tatamiNum * 2 + 1];
        VSCounting = new int[tatamiNum * 2 + 1];
        HSCounting = new int[tatamiNum * 2 + 1];
        idList = new int[tatamiNum];
    }

    void makeTatami(){
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

        for(int i = 0; i < 2 * k + 1; i++){
            for(int j = 0; j < 2 * k + 1; j++){
                pl = new int[] {j, i, 0 , 0};
                if(j == 0 && i != 0){
                    VS = VSCounting[tatamiCounter - 1];
                    HS = HSCounting[tatamiCounter - 1];
                    direction = maked[tatamiCounter - 1].getDirection();
                    if(i == 1 || i == 2 * k){
                        directionSwitcher = 0;
                    }

                }
                if(i < k){
                    if(j != 0){
                        if(directionSwitcher == 0){
                            direction = 0;
                            VS = 1;
                            if((i == 0 && tatamiCounter % 2 != 0) || (j != 0 && HSCounting[tatamiCounter - 1] == 0)){
                                HS = 1;
                            }else if(HSCounting[tatamiCounter - 1] == 1 && maked[tatamiCounter - 1].getDirection() == 0){
                                HS = 0;
                            }else{
                                HS = 0;
                            }

                            if(j == 2 * k - 1 - i){
                                directionSwitcher = 1;
                                HS = 1;
                                VS = 1;
                            }

                        }else{
                            if(j != 0){
                                direction = 1;
                                HS = 1;

                                if(maked[tatamiCounter - 1].getDirection() == 0){
                                    VS = 0;
                                }

                                if(j == i - 1){
                                    directionSwitcher = 0;
                                    VS = 0;
                                }

                                if(tatamiCounter - (k * 2) - 1 >= 0){
                                    if(VSCounting[tatamiCounter - (k * 2) - 1] == 1){
                                        VS = 0;
                                        HS = 1;
                                    }else{
                                        VS = 1;
                                        HS = 1;
                                    }
                                }
                            }
                        }
                    } 
                }else if(i == k){
                    if(j != 0){
                        HS = 1;
                        direction = 1;
                        if(VSCounting[tatamiCounter - 1] == 0 && j != 0){
                            VS = 1;
                            if(j == k){
                                VS = 2;
                            }
                        }else if(VSCounting[tatamiCounter - 1] == 2){
                            if(k == 1){
                                VS = 1;
                            }else{
                                VS = VSCounting[tatamiCounter - 3];
                            }
                            
                        }else if(k == 1 && j == 2 * k){
                            VS = 1;
                        }else{
                            VS = 0;
                        }
                    }
                }else if(i > k){
                    if(j != 0){
                        if(directionSwitcher == 1){
                            direction = 1;
                            HS = 1;
                            if(maked[tatamiCounter - 1].getDirection() == 0 || (VSCounting[tatamiCounter - 1] == 0)){
                                VS = 1;
                            }else{
                                VS = 0;
                            }

                            if(j == 2 * k - i){
                                directionSwitcher = 0;
                            }

                        }else{
                            VS = 1;
                            direction = 0;
                            if(HSCounting[tatamiCounter - 1] == 0){
                                HS = 1;
                            }else{
                                HS = 0;
                            }

                            if(j >= i && j != 2 * k){
                                directionSwitcher = 1;
                            }
                        }
                    }
                }
                
                if(tatamiCounter > 0 && direction == 0 && maked[tatamiCounter - 1].getDirection() == 0 && HSCounting[tatamiCounter - 1] == 0){
                    id = maked[tatamiCounter - 1].getID();
                }else if(tatamiCounter - (k * 2) - 1 >= 0 && direction == 1 && maked[tatamiCounter - (k * 2) - 1].getDirection() == 1 && VSCounting[tatamiCounter - (k * 2) - 1] == 0){
                    id = maked[tatamiCounter - (k * 2) - 1].getID();
                }else if(i == k && j == k){
                    id = 100;
                }else if(tatamiCounter == 0){
                }else{
                    id++;
                    if(this.isUsed(id)){
                        id = idList[idCounter - 1] + 1;
                        idList[idCounter] = id;
                        idCounter++;
                    }else{
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

    boolean isUsed(int a){
        boolean current = false;
        for(int i = 0; i < idList.length; i++){
            if(a == idList[i]){
                current = true;
                break;
            }
        }
        return current;
    }

    boolean isSpreadable(){
        boolean ans = false;
        int i = 1;
        while(true){
            int suuretu = 2 * i * i + 2 * i;
            if(suuretu == tatamiNum){
                ans = true;
                break;
            }else if(suuretu > tatamiNum){
                break;
            }
            i++;
        }
        return ans;
    }

    int determineLines(){
        int j = 1;
        while(true){
            int suuretu = 2 * j * j + 2 * j;
            if(suuretu == tatamiNum){
                break;
            }
            j++;
        }
        return j;
    }
 }
