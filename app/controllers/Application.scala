package controllers

import domain._
import play.api.mvc.{Action, Controller}
import policies.HospitalPolicy._
import stapl.core.{NotApplicable, Deny, Permit}
import stapl.core.pdp.PDP

object Application extends Controller {
  private val insufficientPermissions = "You don't have enough permissions to preform this action."
  private val operationNotAllowed = "Operation not allowed"

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createRecord(physician: Physician, patient: Patient,
                   category: Speciality, content: String): Either[String, Long] = {
    val pdp = new PDP(writePolicies)

    pdp.evaluate("physician", "write", "medicalrecord",
      subject.speciality -> physician.speciality.toString, resource.kind -> category.toString).decision match {
      case Permit =>
        Right(Database.addMedicalRecord(physician, category, patient, content).id)
      case Deny =>
        Left(insufficientPermissions)
      case NotApplicable =>
        Left(operationNotAllowed)
    }
  }

  def readRecord(physician: Physician, patient: Long, medicalRecord: Long): Either[String, String] = {
    Database.getMedicalRecord(medicalRecord) match {
      case Some(record) =>
        val pdp = new PDP(readPolicies)

        pdp.evaluate("physician", "read", "medicalrecord",
          subject.speciality -> physician.speciality.toString,
          subject.treatingPatients -> physician.treatingPatients.map(_.id).toList,
          resource.kind -> record.kind.toString, resource.ownerId -> patient).decision match {
          case Permit =>
            Right(record.content)
          case Deny =>
            Left(insufficientPermissions)
          case NotApplicable =>
            Left(operationNotAllowed)
        }
      case _ => Left("Medical Record not found")
    }
  }

  def updateRecord(physician: Physician, medicalRecord: Long, content: String): Either[String, Boolean] = {
    Database.getMedicalRecord(medicalRecord) match {
      case Some(record) =>
        val pdp = new PDP(writePolicies)

        pdp.evaluate("physician", "write", "medicalrecord",
          subject.speciality -> physician.speciality.toString, resource.kind -> record.kind.toString).decision match {
          case Permit =>
            Right(Database.updateMedicalRecord(medicalRecord, content))
          case Deny =>
            Left(insufficientPermissions)
          case NotApplicable =>
            Left(operationNotAllowed)
        }
      case _ =>
        Left("Medical Record not found")
    }
  }
}