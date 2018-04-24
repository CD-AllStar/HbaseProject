package Client;

import Apps.Conf;
import Apps.Task;
import Util.Util;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EsClient {
    Logger logger=Logger.getLogger(EsClient.class);
    public static BulkProcessor bulkProcessor = null;
    public static Client client = null;
    private static EsClient esInstance=null;
    public static EsClient getInstance(){
        try {
            if(esInstance==null){
                esInstance= new EsClient();
            }
        } catch (UnknownHostException e){
            e.printStackTrace();
    }
    return esInstance;
    }
    private EsClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", Conf.getInstance().getCluster_name())
                .put("client.transport.sniff", true)
                .put("client.transport.ping_timeout", "180s")
                .put("client.transport.nodes_sampler_interval", "10s")
                .build();
        String[] esnodes = Conf.getInstance().getNodes().split(",");
        client = new PreBuiltTransportClient(settings);
        if (esnodes.length > 1) {
            for (String esnode : esnodes) {
                String nodePort = esnode.split(":")[1];
                String nodeIp = esnode.split(":")[0];
                client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIp), Integer.valueOf(nodePort)));
            }
        } else {
            String nodePort = Conf.getInstance().getNodes().split(":")[1];
            String nodeIp = Conf.getInstance().getNodes().split(":")[0];
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(nodeIp), Integer.valueOf(nodePort)));
        }
        ElasticSearchBulkOperator();
    }

    private  void ElasticSearchBulkOperator() {
        bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            public void beforeBulk(long l, BulkRequest bulkRequest) {

            }

            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
               System.out.println(Util.toTime(System.currentTimeMillis())+",rowkey:"+ Task.rowkey+",path:"+ Task.pathName);
               logger.info("rowkey:"+ Task.rowkey+",path:"+ Task.pathName);
            }
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {throwable.printStackTrace();

            }
        }).setBulkActions(Integer.valueOf(Conf.getInstance().getBulkNum()))
                .setBulkSize(new ByteSizeValue(100, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(1))
                .setConcurrentRequests(Integer.valueOf(Conf.getInstance().getConcurrentRequests()))
                .build();
    }
    public void close(){
        if(bulkProcessor!=null){
            bulkProcessor.close();
        }
        if(client!=null){
            client.close();
        }
    }

}
