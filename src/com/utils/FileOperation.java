package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSS on 2017/1/7.
 */
public class FileOperation {
    public static String[] getFileName(String dirStr) {
        File file = new File(dirStr);
        String [] filesArr  = file.list();

        return filesArr;
    }

    /**
     * 写文件
     * 将结果写入文件中，一行表示一个object的信息
     * @param filePath
     * @param resultList
     */
    public static void writeResult(String filePath, List<List<Integer>> resultList, String fileName) {
        String rowContent = null; // 一个object的信息
        String imgContent = ""; // 一个image的信息
        for(int i = 0; i < resultList.size(); i ++) {
            rowContent = "";
            List<Integer> rowData = resultList.get(i);
            for(int j = 0; j < rowData.size() - 1; j ++) {
                rowContent += String.valueOf(rowData.get(j)) + " ";
            }
            rowContent += rowData.get(rowData.size() - 1) + "\n";
            imgContent += rowContent;
        }

        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(fileName + "\n");
            fw.write(imgContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) {
//        String dirStr = "E:\\研究生\\研二下\\ICPR\\experiments\\spit_frames\\ch3_train_frames\\";
//        String [] filesArr = FileOperation.getFileName(dirStr);
//
//        for(int i = 0; i < filesArr.length; i ++) {
//            System.out.println(filesArr[i]);
//        }
//
//        System.out.println("size:" + filesArr.length);

        List<Integer> rowResult1 = new ArrayList<>();
        rowResult1.add(1);
        rowResult1.add(3);
        rowResult1.add(5);
        rowResult1.add(22);

        List<Integer> rowResult2 = new ArrayList<>();
        rowResult2.add(34);
        rowResult2.add(312);
        rowResult2.add(44);
        rowResult2.add(65);

        List<List<Integer>> resultList = new ArrayList<>();
        resultList.add(rowResult1);
        resultList.add(rowResult2);
        FileOperation.writeResult("./abc.txt", resultList, "img1.png");

    }
}
