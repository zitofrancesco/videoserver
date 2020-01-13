package com.unict.riganozito

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.Queue

object App {

  val conf = new SparkConf().setAppName("spark-kafka")
  val ssc = new StreamingContext(conf, Seconds(30))

  val avg_time = new Queue[Double]
  val avg_req = new Queue[Double]

  /* Configure Kafka */
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "PLAINTEXT://<localhost>:9092",
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
  val avg = flat.reduce((x, y) => (x._1 + y._1, x._2 + y._2))


  /* Calculate the average of the response time */
  val lines1 = stream.map(_.value)
  val flat1 = lines1.flatMap(line => line.split(" ")).filter(number => number.contains("@")).map(element => (element.replace("@", "").toDouble, 1))
  val avg1 = flat1.reduce((x, y) => (x._1 + y._1, x._2 + y._2))


  // Ricavo i valori
  val r_avg = avg._1.floatValue/(30*avg._2.intValue)
  val t_avg = avg1._1.floatValue/(30*avg1._2.intValue)

  ssc.start()
  ssc.awaitTermination()

  var global_req_sum, global_time_sum, global_req_avg, global_time_avg = 0.0

  for(i <- 0 to avg_req.length){
    global_req_sum += avg_req(i-1)
    global_time_sum += avg_time(i-1)
  }

  global_req_avg = global_req_sum/avg_req.length
  global_time_avg = global_time_sum/avg_time.length

  var perc_time = ((t_avg-global_time_avg)/global_time_avg)*100
  var perc_req = ((r_avg-global_req_avg)/global_req_avg)*100

  if(perc_time>20){
    if(perc_req>20){
      println("Incremento del tempo medio di risposta pari a %f. Registrato anche un incremento medio del numero di richieste pari a %f", perc_time, perc_req)
    }
    else{
      println("Incremento del tempo medio di risposta pari a %f. Non è stato registrato un incremento considerevole nel numero medio di richieste", perc_time)
    }
  }

  if(avg_req.length==10){
    avg_req.dequeue()
    avg_time.dequeue()
  }
  avg_req += r_avg
  avg_time += t_avg

}