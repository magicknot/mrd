import java.time.LocalDateTime

import controllers.Application
import domain.{Database, Cardiology}


object MRD extends App {
  val d = Database.addPhysician("Antonio", Cardiology)
  val p = Database.addPatient("Manuel", LocalDateTime.now())

  Application.createRecord(d, p, Cardiology, "Teve um enfarte") match {
    case Right(id) =>
      println(Application.readRecord(d, p.id, id).merge)
      Application.updateRecord(d, id, "Teve um enfarte bastante severo")
      println(Application.readRecord(d, p.id, id).merge)
    case Left(error) => println(error)
  }
}
