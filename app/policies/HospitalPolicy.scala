package policies

import stapl.core._
import language.postfixOps

object HospitalPolicy extends BasicPolicy {
  import stapl.core.dsl._

  subject.role = SimpleAttribute(String)
  subject.treatingPatients = ListAttribute(Number)
  resource.kind = SimpleAttribute(String)
  resource.ownerId = SimpleAttribute(Number)

  val viewPolicies = Policy("Access data policies") := when (subject.id === "physician" & action.id === "read") apply DenyOverrides to (
    Rule("policy:1") := deny iff !(resource.ownerId in subject.treatingPatients),
    Rule("policy:2") := deny iff !(subject.role == resource.kind)
  )

  val writePolicies = Policy("Write data policies") := when (subject.id === "physician" & action.id === "write") apply PermitOverrides to (
    Rule("policy:3") := permit iff (subject.role === resource.kind),
    Rule("policy:4") := deny
  )

}
