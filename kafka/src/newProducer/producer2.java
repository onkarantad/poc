package newProducer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.StringSerializer;

public class producer2 {
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		String bootstrapServers = "10.201.10.16:9092";// 10.245.128.143:9092

		Properties properties = new Properties();
//		properties = loadConfig("kafkaConfigProps.properties");

		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		int nos = 1;
		int from = 13;
		int to = nos + from - 1;

		String topic = "IDIT_Source_Data";
		int numPartitions = 3;
		//createTopic(topic, properties, numPartitions);
		
		String key = "policy_1113975_P1077442_2";
		
		String contact = "";
		
		ProducerRecord<String, String> record=new ProducerRecord<String, String>(topic,key, 
				contact);
		producer.send(record);

		producer.flush();
		producer.close();
		System.out.println("msg sent");
	}

	public static Properties loadConfig(final String configFile) throws IOException {
		if (!Files.exists(Paths.get(configFile))) {
			throw new IOException(configFile + " not found.");
		}
		final Properties cfg = new Properties();
		try (InputStream inputStream = new FileInputStream(configFile)) {
			cfg.load(inputStream);
		}
		return cfg;
	}

	public static void createTopic(final String topic, final Properties cloudConfig, int numPartitions)
			throws InterruptedException, ExecutionException {

		final NewTopic newTopic = new NewTopic(topic, numPartitions, (short) 3);

		AdminClient admin = AdminClient.create(cloudConfig);

		boolean alreadyExists = admin.listTopics().names().get().stream()
				.anyMatch(existingTopicName -> existingTopicName.equalsIgnoreCase(topic.toString()));

		if (alreadyExists) {
			System.out.printf("topic already exits: %s%n", newTopic);
		} else {
			try (final AdminClient adminClient = AdminClient.create(cloudConfig)) {
				adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
			} catch (final InterruptedException | ExecutionException e) {
				// Ignore if TopicExistsException, which may be valid if topic exists
				if (!(e.getCause() instanceof TopicExistsException)) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
