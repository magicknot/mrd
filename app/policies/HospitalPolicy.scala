package policies

import stapl.core._
import language.postfixOps

object HospitalPolicy extends BasicPolicy {
  import stapl.core.dsl._

  subject.speciality = SimpleAttribute(String)
  subject.treatingPatients = ListAttribute(Number)
  resource.kind = SimpleAttribute(String)
  resource.ownerId = SimpleAttribute(Number)

  val readPolicies = Policy("Access data policies") := when (subject.id === "physician" & action.id === "read") apply PermitOverrides to (
    Rule("policy:1") := permit iff (resource.ownerId in subject.treatingPatients),
    Rule("policy:2") := permit iff (subject.speciality === resource.kind),
    Rule("policy:3") := deny
  )

  val writePolicies = Policy("Write data policies") := when (subject.id === "physician" & action.id === "write") apply PermitOverrides to (
    Rule("policy:4") := permit iff (subject.speciality === resource.kind),
    Rule("policy:5") := deny
  )

}
