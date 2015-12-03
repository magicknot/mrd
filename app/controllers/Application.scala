package controllers

import domain._
import play.api.mvc.{Action, Controller}
import policies.HospitalPolicy._
import stapl.core.{NotApplicable, Deny, Permit}
import stapl.core.pdp.PDP

object Application extends Controller {
  private val insufficient_permissions = "You don't have enough permissions to preform this action."

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createRecord(physician: Physician, patient: Patient,
                   category: Speciality, content: String): Unit = {
    val pdp = new PDP(writePolicies)

    pdp.evaluate("physician", "write", "medicalrecord",
      subject.role -> physician.speciality.toString, resource.kind -> category.toString).decision match {
      case Permit =>
        println("Conseguiste criar o record")
      case Deny =>
        println(insufficient_permissions)
      case NotApplicable =>
        println("Operation not allowed")
    }
  }

  def readRecord(doctor: Physician, medicalRecord: Long) = ???
  def updateRecord(doctor: Physician, medicalRecord: Long, content: String) = ???
}