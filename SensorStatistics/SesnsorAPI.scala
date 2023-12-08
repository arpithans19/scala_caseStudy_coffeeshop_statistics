package SensorStatistics

case class SensorMeasurment(sensorid:String,humidity : String){}
object SesnsorAPI {
  def main(args: Array[String]): Unit = {

    try {
      val directory = "C:\\Users\\ARPITHAN\\Desktop\\scala\\ScalaFiles"
      val sensor = new SensorImpl()
      val noFiles = sensor.listFiles(directory)
      println("Num of processed files: " + noFiles)
      val allMeasurements = sensor.readFiles(directory)
      println("Num of processed measurements: " + allMeasurements)

      val failedMeasure = sensor.failedMeasurements(directory)
      println("Num of failed measurements: " + failedMeasure)

      sensor.minAvgMaxHumidity(directory)
    }
    catch {
      case i:NullPointerException=>println("Directory doesn't exists ")

    }


  }
}