
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class HbaseDataSyncKafkaObserver extends BaseRegionObserver{
        private static Producer<String, String> producer = null;
        private static String kafka_servers;
        private static String zk_connect;
        private static String kafka_topic;
        private static final Log logger = LogFactory.getLog(HbaseDataSyncKafkaObserver.class);
        private static void readConfiguration(CoprocessorEnvironment env) {
            Configuration conf = env.getConfiguration();
            kafka_servers = conf.get("kafka_servers");
            zk_connect = conf.get("zk_connect");
            kafka_topic=conf.get("kafka_topic");
        }

        public void start(CoprocessorEnvironment e) throws IOException {
            readConfiguration(e);
            Properties properties = new Properties();
            properties.put("bootstrap.servers",kafka_servers );//192.168.126.10:9092
            properties.put("retries", 1);
            properties.put("zk.connect", zk_connect);//master:2181
            properties.put("batch.size",1048576);
            properties.put("buffer.memory",67108864);
            properties.put("receive.buffer.bytes",65536);
            properties.put("max.request.size",1048576);
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producer = new KafkaProducer<String, String>(properties);
        }

        public void stop(CoprocessorEnvironment e) throws IOException {
            producer.flush();
        }
        public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            String indexId = new String(put.getRow());
            logger.info("-----a put in "+tableName+",rowkey:"+indexId);
            for (List<Cell> cells : put.getFamilyCellMap().values()) {
                for (Cell cell : cells) {
                    String column = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String values = Bytes.toString(CellUtil.cloneValue(cell));
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(kafka_topic, indexId, String.format("%s:%s", column, values));
                    producer.send(producerRecord);
                }
            }
        }
        public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
            String tableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
            String indexId1 = new String(delete.getRow());
            logger.info("------a delete in :" + tableName + ",rowkey:" + indexId1);
            ProducerRecord<String,String> producerRecord=new ProducerRecord<String, String>(kafka_topic,indexId1,"deleteRowkey");
            producer.send(producerRecord);
        }
}
