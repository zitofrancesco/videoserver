package com.unict.riganozito

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {

  val conf = new SparkConf().setAppName("spark-kafka")
  val ssc = new StreamingContext(conf, Seconds(30))

  val avg_time : Array[Double] = new Array(10)
  val avg_req : Array[Double] = new Array(10)

  /* Configure Kafka */
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9006",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("videoservice.topic.spark")
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  /* Calculate the average of the number of requests */
  val lines = stream.map(_.value)
  val flat = lines.flatMap(line => line.split(" ")).filter(number => number.contains("#")).map(element => (element.replace("#", "").toDouble, 1))
  val avg = flat.reduce((x, y) => (x._1 + y._1, x._2 + y._2)).map{case (sum, count) => (1.0 * sum)/(30 *count)}


  /* Calculate the average of the response time */
  val lines1 = stream.map(_.value)
  val flat1 = lines1.flatMap(line => line.split(" ")).filter(number => number.contains("@")).map(element => (element.replace("@", "").toDouble, 1))
  val avg1 = flat1.reduce((x, y) => (x._1 + y._1, x._2 + y._2)).map{case (sum, count) => (1.0 * sum)/(30*count)}


  // RICAVARE I VALORI

  ssc.start()
  ssc.awaitTermination()

}
