package CoffeShopStatitics

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.math.Ordering.Implicits.{infixOrderingOps, seqOrdering}

class CoffeeShopImplementaion extends CoffeeShopTrait {

  override def listOfFiles(dir: String): Int = {
    val file = new java.io.File(dir).listFiles.filter(_.isFile).filter(_.getName.endsWith(".csv"))
    file.size
  }

  override def processedCustomerData(dir: String): Int = {
    val file = new java.io.File(dir).listFiles.filter(_.isFile).filter(_.getName.endsWith(".csv"))

    val measurements = file.map(x => Source.fromFile(x).getLines().toList)

    var count = 0
    for (m <- measurements) {
      count = count + m.tail.length
    }
    count
  }

  override def failedCustomerData(dir: String): Int = {
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


  override def minAvgMaxQuantity(path: String): Unit = {

    val categories = new ListBuffer[String]()
    val quantity = new ListBuffer[ListBuffer[String]]()

    val resultBuffer = new ListBuffer[ListBuffer[String]]()
    val appendResult = new ListBuffer[String]
    val fileList = new File(path).listFiles.filter(_.getName.endsWith(".csv")).toList
    val listNaN: ListBuffer[String] = ListBuffer()

    for (files <- fileList) {
      val getLines = Source.fromFile(files).getLines()
      for (lines <- getLines) {
        val splitValue = lines.split(",").toList

        if (splitValue(2) != "category") {
          if (!categories.contains(splitValue(2).toLowerCase())) {
            categories += splitValue(2).toLowerCase()

            val quantityList = ListBuffer[String]()

            quantityList += splitValue(3)
            quantity += quantityList

          }
          else {
            val yy = categories.indexOf(splitValue(2).toLowerCase())
            if (splitValue(3).matches("[^[a-zA-Z]]*$")) {
              if (0 < splitValue(3).toInt && splitValue(3).toInt <= 100) {
                quantity(yy) += splitValue(3)
              }
            }
            else {
              if (splitValue(3) == "NaN") {
                quantity(yy) += splitValue(3)
              }
            }
          }
        }
      }
      }
//   }   println(quantity)
//      println(categories)

    println("category,min,avg,max")


      for (id <- 0 until categories.length) {

        val quantityList = quantity(id)
        var sum = 0
        var avg = 0.0
        var count = 0
        var median = 0
        val ls: ListBuffer[Int] = ListBuffer()

        for (values <- 0 until quantityList.length) {
          if (quantityList(values) != "NaN") {
            sum += quantityList(values).toInt
            ls += quantityList(values).toInt
            count = count + 1
          }
        }

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
          result += categories(id)
          resultBuffer += result


        }
        else {
          listNaN += categories(id)
        }


      }



      for (i <- resultBuffer.sorted) {
        println(i(4) + "," + i(1) + "," + i(0) + "," + i(2) )
      }
      for (i <- listNaN) {
        println(i + ",NaN,NaN,NaN,NaN")
      }
    }


  }