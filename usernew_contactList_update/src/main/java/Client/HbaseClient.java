package Client;

import Apps.Conf;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HbaseClient {
    private static Configuration configuration;
    private  Table contactTable;
    private  Table userTable;
    private static Connection connection;
    private static HbaseClient Instance;
        static {
        configuration = HBaseConfiguration.create();
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        configuration.set("hbase.zookeeper.quorum", Conf.getInstance().getZKconnect());
        configuration.set("hbase.ZooKeeper.property.clientPort", Conf.getInstance().getZKport());
        configuration.setInt("zookeeper.recovery.retry", 3);
        configuration.setInt("hbase.rpc.timeout", 120000);
        configuration.setInt("hbase.client.operation.timeout", 60000);
        configuration.setInt("hbase.client.scanner.timeout.period", 1800000);
    }

    public static HbaseClient getInstance() {
        if(Instance==null){
             Instance=new HbaseClient();
        }
        return Instance;
    }

    private HbaseClient() {
        try {
            if(connection==null){
           connection = ConnectionFactory.createConnection(configuration);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Table InitContactTable(){
        try {
            if(contactTable==null){
                contactTable=connection.getTable(TableName.valueOf(Conf.getInstance().getContactTable()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactTable;
    }
    public Table InitUserTable(){
        try {
            if(userTable==null){
                userTable=connection.getTable(TableName.valueOf(Conf.getInstance().getUserTable()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userTable;
    }
    public  void close() {
        try {
            if(connection!=null){
                connection.close();
            }
            if(contactTable!=null){
                contactTable.close();
            }
            if(userTable!=null){
                userTable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
