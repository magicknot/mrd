package policies

import stapl.core._
import language.postfixOps

object HospitalPolicy extends BasicPolicy {
  import stapl.core.dsl._

  subject.department = SimpleAttribute(String)
  subject.location = SimpleAttribute(String)

  val physician = subject.refine()
  physician.treatingPatients = ListAttribute(Number)

  val nurse = subject.refine()
  nurse.beginOfShift = SimpleAttribute(Time)
  nurse.endOfShift = SimpleAttribute(Time)

  resource.department = SimpleAttribute(String)
  resource.creationDate = SimpleAttribute(DateTime)
  resource.lastChange = SimpleAttribute(DateTime)
  resource.ownerId = SimpleAttribute(Number)

  environment.currentTime = SimpleAttribute(Time)
  environment.currentDay = SimpleAttribute(DateTime)
  environment.location = SimpleAttribute(String)

  var viewPolicies = Policy("Policies to view medical records") := when(action.id === "view") apply PermitOverrides to (
    // Policy defining rules to Nurses
    Policy("nurses") := when(subject.id === "nurse") apply PermitOverrides to (
      // Nurses can only view data if they are in shift
      Rule("in shift") := deny iff !((environment.currentTime gteq nurse.beginOfShift) & (environment.currentTime lteq nurse.endOfShift)),
      // Nurses can only view data belonging to their department
      Rule("own department") := deny iff !(subject.department === resource.department),
      // Nurses can only view data of the last trimester
      Rule("medical record was created on the last trimester") := deny iff !(environment.currentDay lteq (resource.creationDate + 3.months)),
      // Deny otherwise
      Rule("default-deny") := deny
    ),

    // Policy defining rules to Physicians
    Policy("physicians") := when (subject.id === "physician") apply PermitOverrides to (
      // Physicians can see records from their own department
      Rule("same department") := permit iff (subject.department === resource.department),
      // Physicians can read all records from their patient list
      Rule("own patients") := permit iff (resource.ownerId in physician.treatingPatients),
      // Deny otherwise
      Rule("default-deny") := deny
      )
    )

  //PolicySet to create/update medical records
  val writePolicies = Policy("Write data policies") := when (subject.id === "physician" & action.id === "write") apply PermitOverrides to (
    Rule("policy:4") := permit iff (subject.department === resource.department),
    Rule("policy:5") := deny
  )

}
