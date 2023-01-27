package oldProducer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class producer1 {
    public static void main(String[] args) throws IOException {
        String bootstrapServers = "kafkamaster:9092";// 10.245.128.143:9092

        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        String content = readFile("contact_test2.json");

        ProducerRecord<String, String> record1 = new ProducerRecord<String, String>("IDIT_Source_Data", "contact_1000000_CT1000000_1", content);

        producer.send(record1);
        producer.flush();
        producer.close();
        System.out.println("msg sent");
    }

    static String readFile(String path) throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
