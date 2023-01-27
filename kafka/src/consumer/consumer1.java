package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;  
import org.apache.kafka.clients.consumer.ConsumerRecord;  
import org.apache.kafka.clients.consumer.ConsumerRecords;  
import org.apache.kafka.clients.consumer.KafkaConsumer;  
import org.apache.kafka.common.serialization.StringDeserializer;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import java.time.Duration;  
import java.util.Arrays;  
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;  
public class consumer1 {  
    //  static String className = this.getClass().getSimpleName();
    public static void main(String[] args) {
        //      System.out.println(this.getClass().getSimpleName());
        Logger logger = LoggerFactory.getLogger(consumer1.class.getName());  
        String bootstrapServers = "10.245.128.143:9092";  
        String grp_id = "console-consmer-test4-33";//"console-consmer-test1-33";//"console-consmer-test1-33";  
        String topic = "IDIT_SRC_DATA";//"TestSII_IDIT_SOURCE_DATA";//"Test_IDIT_SOURCE_DATA";//"testSSI1";//"SSI_Test_MongoDB1";//"SSIErrorTopic";//"testSSI";
        //Creating consumer properties  
        Properties properties=new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());  
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());  
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,grp_id);  
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");  
        //creating consumer  
        KafkaConsumer<String, String> consumer= new KafkaConsumer<String,String>(properties);  
        //Subscribing  
        consumer.subscribe(Arrays.asList(topic));  
        //polling  
        while(true){  
              ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(1000)); 
//            logger.info("msg");
//            System.out.println(records.count());
            for(ConsumerRecord<String,String> record: records){ 
//               logger.info("Key: "+ record.key());
				logger.info("Key: " + record.key() /* + ", Value:" +record.value() */);
                logger.info("Key: " + record.value() /* + ", Value:" +record.value() */);
                logger.info("Partition:" + record.partition()+",Offset:"+record.offset()+","+record.timestamp());  
            }  
            /*
            ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(1)); 
        //  System.out.println(records.count());
            Iterator<ConsumerRecord<String, String>> iterator = records.iterator();//10
            if (iterator.hasNext()) 
//              list<> = iterator.next().value()
                System.out.println(iterator.next().value());*/
        }  
    }  
}
