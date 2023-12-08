package SensorStatistics

trait SesnsorTrait {
  def listFiles(dir: String): Int

  def readFiles(dir: String): Int

  def failedMeasurements(dir: String):Int

  def minAvgMaxHumidity(dir: String): Unit

//  def sortedHumidity():Unit



}
