package parcial.chat
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case object Subscribe
case object Unsubscribe

case class Notification(event: String)

case class SendMessage(content: String)


class ChatRoom extends Actor {
  private var list: Set[ActorRef] = Set()

  def receive: Receive = {
    case Subscribe =>
      list.foreach(_ ! Notification(s"${sender.path.name} has subscribed"))
      list = list + sender
    case Unsubscribe =>
      list = list - sender
      list.foreach(_ ! Notification("A user has left the chat room"))
    case msg@SendMessage(content) =>
      if(list.contains(sender)) (list-sender).foreach(_.forward(msg))
  }
}

class ChatParticipant extends Actor {
  def receive: Receive = {
    case Notification(event) =>
      println(s"A new event ocurred in chat room: $event")
    case SendMessage(content) =>
      println(s"A new message has arrived from ${sender().path.name}: $content")
  }
}

object Tester extends App {
  val actorSystem = ActorSystem("actorSystem")
  val chatRoom = actorSystem.actorOf(Props[ChatRoom], "soyChatRoom")
  val apu: ActorRef = actorSystem.actorOf(Props[ChatParticipant], "apu")
  val jorge = actorSystem.actorOf(Props[ChatParticipant], "jorge")

  chatRoom.tell(Subscribe, apu)
  chatRoom.tell(Subscribe, jorge)
  chatRoom.tell(SendMessage("Buen dia grupo soy jorge"), jorge)
}
