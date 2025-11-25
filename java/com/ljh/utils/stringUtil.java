package com.ljh.utils;

import java.util.List;
import java.util.UUID;

public class stringUtil {
    public static String getUUID(){
        String[] list = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L"
        ,"M","N","O","P","Q","R","S","T","U","V","X","Y","Z"};

        StringBuilder uuid = new StringBuilder();
        for(int i=0;i<10;i++){
            uuid.append(list[i]);
        }
        return uuid.toString();
    }
}
