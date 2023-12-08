//package poc
//
//import org.apache.kafka.streams.kstream.Materialized
//import org.apache.kafka.streams.scala.StreamsBuilder
//import org.apache.kafka.streams.scala.kstream.{KStream, KTable}
//import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
//
//import java.time.Duration
//import java.util.Properties
//
//object WordCount2 extends App {
//
//  val props: Properties = {
//    val p = new Properties()
//    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application")
//    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//    p
//  }
//
//  val builder: StreamsBuilder = new StreamsBuilder
//  val textLines: KStream[String, String] = builder.stream[String, String]("TextLinesTopic")
//  val wordCounts: KTable[String, Long] = textLines
//    .flatMapValues(textLine => textLine.toLowerCase.split("\\W+"))
//    .groupBy((_, word) => word)
//    .count()(Materialized.as("counts-store"))
//  wordCounts.toStream.to("WordsWithCountsTopic")
//
//  val streams: KafkaStreams = new KafkaStreams(builder.build(), props)
//  streams.start()
//
//  sys.ShutdownHookThread {
//    streams.close(Duration.ofMillis(10))
//  }
//}
