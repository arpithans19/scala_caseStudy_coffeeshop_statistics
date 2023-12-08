package SensorStatistics


import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.math.Ordering.Implicits.{infixOrderingOps, seqOrdering}

class SensorImpl extends SesnsorTrait {

//  val dir = "C:\\Users\\ARPITHAN\\Desktop\\ScalaFiles"
  var mean = ""
  var map: Map[String, ListBuffer[Int]] = Map()


  override def listFiles(dir: String): Int = {
    val file = new java.io.File(dir).listFiles.filter(_.isFile).filter(_.getName.endsWith(".csv"))
    file.size
  }


  override def readFiles(dir: String): Int = {
    val file = new java.io.File(dir).listFiles.filter(_.isFile).filter(_.getName.endsWith(".csv"))
    val measurements = file.map(x => Source.fromFile(x).getLines().toList)
    var count = 0
    for (j <- measurements) {
      count = count + j.tail.length
    }
    count

  }


  override def failedMeasurements(dir: String): Int = {

    val file = new java.io.File(dir).listFiles.filter(_.isFile).filter(_.getName.endsWith(".csv"))
    val measurements = file.map(x => Source.fromFile(x).getLines().toList)
    val x = measurements.flatten
    var count = 0
    for (j <- 0 until x.length) {
      if (x(j).contains("NaN")) {
        count = count + 1
      }
    }
    count

  }


  override def minAvgMaxHumidity(dir: String): Unit = {
    val sensorId = new ListBuffer[String]()
    val humidity = new ListBuffer[ListBuffer[String]]()
    val resultBuffer = new ListBuffer[ListBuffer[String]]()
    val appendResult = new ListBuffer[String]
    val fileList = new File(dir).listFiles.filter(_.getName.endsWith(".csv")).toList
    val listNaN: ListBuffer[String] = ListBuffer()

    for (lF <- fileList) {
      val getLines = Source.fromFile(lF).getLines()
      for (gL <- getLines) {
        val splitValue = gL.split(",").toList
        //        println(splitValue(0))        //sensor-Id
        //        println(splitValue(1))        //humidity

        if (splitValue(0) != "sensor-id") {
          if (!sensorId.contains(splitValue(0).toLowerCase)) {
            sensorId += splitValue(0).toLowerCase()

            val humdityList = new ListBuffer[String]()

            if (splitValue(1).matches("[^[a-zA-Z]]*$")) {
              if (0 < splitValue(1).toInt && splitValue(1).toInt <= 100) {
                humdityList += splitValue(1)
              }
            }
            else {
              if (splitValue(1) == "NaN") {
                humdityList += splitValue(1)
              }
            }
            humidity += humdityList
          }

          else {
            val indexOfS = sensorId.indexOf(splitValue(0))
            if (splitValue(1).matches("[^[a-zA-Z]]*$")) {
              if (0 < splitValue(1).toInt && splitValue(1).toInt <= 100) {
                humidity(indexOfS) += splitValue(1)
              }
            }
            else {
              if (splitValue(1) == "NaN") {
                humidity(indexOfS) += splitValue(1)
              }
            }
          }

        }
      }
    }

//        println(sensorId)
//        println(humidity)




    println("Sensors with highest avg humidity:")
    println("sensor-id,min,avg,max,median")


    for (id <- 0 until sensorId.length) {

      val humidityList = humidity(id)
      var sum = 0
      var avg = 0
      var count = 0
      var median = 0
      val ls: ListBuffer[Int] = ListBuffer()

      for (values <- 0 until humidityList.length) {
        if (humidityList(values) != "NaN") {
          sum += humidityList(values).toInt
          ls += humidityList(values).toInt
          count = count + 1
        }
      }
      //      println(sensorId(id) + "," + ls.min + "," + ls.max)
      //      println(ls.max)
      //      println(ls.min)
      //      if (count == 0) {
      //        HumidityNaNValuesNaN += sensorId(id)
      //      }
      if (count != 0) {
        avg = sum / count

        val sortedLs = ls.sorted

        //even
        if (sortedLs.length % 2 == 0) {
          val division = sortedLs.length / 2
          val n = sortedLs(division) + sortedLs(division - 1)
          median = n / 2
        }
        else {
          val t = sortedLs.length / 2
          median = sortedLs(t)
        }

        val result = appendResult.empty
        result += avg.toString
        result += ls.min.toString
        result += ls.max.toString
        result += median.toString
        result += sensorId(id)
        resultBuffer += result


      }
      else {
        listNaN += sensorId(id)
      }



    }
//    println(resultBuffer)
//    println(resultBuffer.sorted)
//    //println(Nan)


    for (i <- resultBuffer.sorted.reverse) {
//    for (i <-  resultBuffer.sortWith(_>_)) {
      println(i(4) + "," + i(1) + "," + i(0) + "," + i(2) + "," + i(3))


    }
    for (i <- listNaN) {
      println(i + ",NaN,NaN,NaN,NaN")
    }
  }


}
