package com.example.demo.util;

import java.io.File;
import java.util.List;


public class FileUtil {

    public static String delImg(List<String> path){
        for(int i=0;i<path.size();i++){
            File file = new File(path.get(i));
            if(file.exists()){
                file.delete();
            }
        }
        return "删除成功";
    }

    public static String delOneImg(String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        return "删除成功";
    }
}
