package com.mouse;

import com.utils.FileOperation;

/**
 * Created by SSS on 2017/1/7.
 */
public class Main {
    // 主入口
    public static void main(String []args) {
        String dirStr = "E:\\研究生\\研三上\\icmr2017\\code\\utils\\trainSet\\sub5\\";
        String resultPath = "./abc.txt";
        String []filesArr = FileOperation.getFileName(dirStr);
        ObjectIdentify obj = new ObjectIdentify();
        obj.setDirStr(dirStr);
        obj.setFilesArr(filesArr);
        obj.setResultPath(resultPath);
        obj.initPane();
    }
}
