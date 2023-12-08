package akka

import akka.actor.{Actor, ActorSystem, Props};

class HelloAkka extends Actor { // Extending actor trait
  def receive = { //  Receiving message
    case msg: String => println(msg)
    case _ => println("Unknown message") // Default case
  }
}

object Main {
  def main(args: Array[String]) {
    val actorSystem = ActorSystem("ActorSystem"); // Creating ActorSystem
    val actor = actorSystem.actorOf(Props[HelloAkka], "HelloAkka") //Creating actor
    actor ! "Hello Akka" // Sending messages by using !
    actor ! 100.52
  }
}  