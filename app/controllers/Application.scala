package controllers

import domain.{Speciality, MedicalRecord, Doctor, Patient}
import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createRecord(doctor: Doctor, patient: Patient, content: String, category: Speciality) = ???

  def readRecord(doctor: Doctor, medicalRecord: Long) = ???

  def updateRecord(doctor: Doctor, medicalRecord: Long, content: String) = ???

}