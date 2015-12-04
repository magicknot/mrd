package domain

import java.time.LocalTime

trait Employee {
  def id: Long
  def department: Department
}

case class Nurse(name: String,
                 beginOfShift: LocalTime, endOfShift: LocalTime,
                 department: Department,
                 id: Long) extends Employee

case class Physician(name: String,
                     department: Department,
                     treatingPatients: Set[Patient] = Set.empty,
                     id: Long) extends Employee
