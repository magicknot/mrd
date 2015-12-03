import java.time.LocalDateTime

import controllers.Application
import domain.{Cardiology, Physician, Patient}


object MRD extends App {
  val d = Physician("Antonio", Cardiology, 0l)
  val p = Patient("Manuel", LocalDateTime.now(), id = 0l)

  Application.createRecord(d, p, Cardiology, "Teve um enfarte")
}
