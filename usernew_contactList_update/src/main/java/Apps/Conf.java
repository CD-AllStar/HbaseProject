package Apps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Conf {
    //es
    private String  index;
    private String type;
    private String cluster_name;
    private String nodes;
    private String bulkNum;
    private String concurrentRequests;
    //ev
    private  String paths;
    private String tmpPath;
    //hbase
    private  String scanNum;
    private String ZKconnect;
    private String ZKport;
    private String contactTable;
    private String userTable;
    Properties props = null;
    private String path= UpdateApp.path;
    private static Conf Instance=null;
    private Conf(){
        props=new Properties();
        try {
            InputStream io=new FileInputStream(path);
            props.load(io);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
    public static Conf getInstance(){
        if(Instance==null){
            Instance=new Conf();
        }
        return Instance;
    }
    public String getIndex() {
        return props.getProperty("index");
    }

    public String getType() {
        return props.getProperty("type");
    }

    public String getCluster_name() {
        return props.getProperty("cluster_name");
    }

    public String getNodes() {
        return props.getProperty("nodes");
    }

    public String getBulkNum() {
        return  props.getProperty("bulkNum");
    }

    public String getConcurrentRequests() {
        return props.getProperty("concurrentRequests");
    }

    public String getPaths() {
        return props.getProperty("paths");
    }

    public String getScanNum() {
        return props.getProperty("scanNum");
    }

    public String getZKconnect() {
        return props.getProperty("ZKconnect");
    }

    public String getZKport() {
        return props.getProperty("ZKport");
    }

    public String getContactTable() {
        return props.getProperty("contactTable");
    }
    public String getUserTable(){return  props.getProperty("userTable");}
    public String getTmpPath(){return props.getProperty("tmpPath");}
}
