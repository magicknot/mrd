package domain.policies

import domain.{MedicalRecord, Doctor}

sealed trait Rule {
  def description: String
  def condition: (Doctor, MedicalRecord) => Boolean
  def compute(subject: Doctor, target: MedicalRecord): Boolean = condition(subject, target)
}

case class Permit(description: String, condition: (Doctor, MedicalRecord) => Boolean) extends Rule

case class Deny(description: String, condition: (Doctor, MedicalRecord) => Boolean) extends Rule {
  override def compute(subject: Doctor, target: MedicalRecord): Boolean = !super.compute(subject, target)
}
