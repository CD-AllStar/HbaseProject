package Util;

import Apps.Task;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Util {
    public static String timeParse(Long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(time);
    }

    public static String toTime(Long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(time);
    }

    public static Long toLong(String time) {
        Long longs = null;
        try {
            longs = Long.valueOf(time);
        } catch (Exception e) {
            System.out.println("String time parse to Long exception." + Task.rowkey);
        }
        return longs;
    }

    public static Long dateTransform(String time) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Long times = null;
        try {
            times = format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("dateTransform faith." + Task.rowkey);
        }
        return times;
    }

    public static List<String> getFileList(String paths, String tmpPath) {
        String time = Util.timeParse(System.currentTimeMillis());
        List<String> fileList = new LinkedList<String>();
        String path;
        if (tmpPath == null || tmpPath.equals("null")) {
            path = paths;
        } else {
            path = tmpPath;
        }
        File file = new File(path);
        if (file.isFile()) {
            fileList.add(file.toString());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                if (files.length > 0) {
                    for (File f : files) {
                        String fileName = f.getName();
                        if (fileName.startsWith(time)) {
                            fileList.add(fileName);
                        }
                    }
                }
            }
        }
        return fileList;
    }
}