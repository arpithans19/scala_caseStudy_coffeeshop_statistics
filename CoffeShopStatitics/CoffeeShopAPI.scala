package CoffeShopStatitics



case class CoffeeShop(customerId:String,customerName: String,category:String,quantity:Integer){}

object CoffeeShopAPI {
  def main(args: Array[String]): Unit = {
    try {

      val dir = "C:\\Users\\ARPITHAN\\Desktop\\scala\\CoffeeShop_CSVFiles"

      val coffeeImpl=new CoffeeShopImplementaion()

      val noFiles = coffeeImpl.listOfFiles(dir)
      println("Num of processed files: " + noFiles)
      val allMeasurements = coffeeImpl.processedCustomerData(dir)
      println("Num of processed Customer Data: " + allMeasurements)

      val failedData = coffeeImpl.failedCustomerData(dir)
      println("Num of failed Customer Data: "+failedData)

     coffeeImpl.minAvgMaxQuantity(dir)
    }
    catch {
      case i:NullPointerException=>println("Directory doesn't exists ")
      case _:Exception=>println("Exception occurs")

    }


  }

}