import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
 * @author elbaghdadianass
 */
class KafkaHelper(createProducer: () => KafkaProducer[String, String]) extends Serializable {

  @transient lazy val producer = createProducer()

  def send(topic: String, value: String): Unit = producer.send(new ProducerRecord(topic, value))
}

object KafkaHelper {
  def apply(config: Properties): KafkaHelper = {
    val f = () => {
      val producer = new KafkaProducer[String, String](config)

      sys.addShutdownHook {
        producer.close()
      }

      producer
    }
    new KafkaHelper(f)
  }
}



