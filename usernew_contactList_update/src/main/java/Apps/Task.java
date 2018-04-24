package Apps;

import Client.EsClient;
import Client.HbaseClient;
import Util.Util;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Task {
    public static String rowkey;
    public static String pathName;
    private static Result[] results;
    public static void excuteTask() throws IOException {
        BufferedReader reader = null;
        EsClient.getInstance();
        List<String> pathList = Util.getFileList(Conf.getInstance().getPaths(), Conf.getInstance().getTmpPath());
        String scanNum = Conf.getInstance().getScanNum();
        List<Get> gets = new LinkedList<Get>();
        for (String path : pathList) {
            if (Conf.getInstance().getTmpPath().equals("null") || Conf.getInstance().getTmpPath() == null) {
                pathName = Conf.getInstance().getPaths() + path;
            } else {
                pathName = path;
            }
            reader = new BufferedReader(new FileReader(new File(pathName)));
            rowkey = reader.readLine();
            while (!((rowkey.contains("-")||rowkey.length() == 32))){
                rowkey = reader.readLine();
                if(rowkey==null||rowkey.equals("null")){
                    System.out.println(rowkey);
                    break;
                }
            }
            if (rowkey!=null&&rowkey.contains("-")) {
                while (null != rowkey) {
                    Get get = new Get(Bytes.toBytes(rowkey));
                    get.setCacheBlocks(false);
                    gets.add(get);
                    if (gets.size() >= Integer.valueOf(scanNum)) {
                        results = HbaseClient.getInstance().InitContactTable().get(gets);
                        ContactListTask.excuteTask(results);
                        gets = new LinkedList<Get>();
                    }
                    rowkey = reader.readLine();
                }
                if (gets.size() > 0) {
                    System.out.println(" finally getsList size" + gets.size());
                    results = HbaseClient.getInstance().InitContactTable().get(gets);
                    ContactListTask.excuteTask(results);
                }
            } else if (rowkey!=null&&rowkey.length() == 32){
                try {
                    HbaseClient.getInstance().InitUserTable();
                    while (null != rowkey) {
                        Get get = new Get(Bytes.toBytes(rowkey));
                        get.setCacheBlocks(false);
                        gets.add(get);
                        if (gets.size() >= Integer.valueOf(scanNum)) {
                            results = HbaseClient.getInstance().InitUserTable().get(gets);
                            UserNewTask.excuteTask(results);
                            gets = new LinkedList<Get>();
                        }
                        rowkey = reader.readLine();
                    }
                } catch (Exception e) {
                    System.out.println("error" + e.getMessage());
                }
                if (gets.size() > 0) {
                    System.out.println(" finally getsList size" + gets.size());
                    results = HbaseClient.getInstance().InitUserTable().get(gets);
                    UserNewTask.excuteTask(results);
                }
            }
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HbaseClient.getInstance().close();
        if (results != null) {
            results.clone();
        }
        if (reader != null) {
            reader.close();
        }
        EsClient.getInstance().close();
    }
}
