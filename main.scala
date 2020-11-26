object main extends App {
  val spark = SparkSession
    .builder()
    .appName("main class")
    .config("spark.master", "local")
    .getOrCreate()
    
   val kafkahelper = spark.sparkContext.broadcast(KafkaHelper(props))
   
   dstream.foreachRDD { rdd =>
    rdd.foreach { message =>
      kafkaSink.value.send("topic_name", message)
                }
      }
}
