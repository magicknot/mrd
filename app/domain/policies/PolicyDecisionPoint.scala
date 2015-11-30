package domain.policies

import domain.{Cardiology, MedicalRecord, Database, Doctor}

case class Request(subject: Doctor, target: Long, action: Action)

object PolicyDecisionPoint {
  private var readPolicies = Set.empty[Rule]
  private var writePolicies = Set.empty[Rule]
  private var updatePolicies = Set.empty[Rule]
  private var deletePolicies = Set.empty[Rule]

  def addPolicy(action: Action, rule: Rule) = action match {
    case Read => readPolicies += rule
    case Write => writePolicies += rule
    case Update => updatePolicies += rule
    case Remove => deletePolicies += rule
  }

  def computeRequest(request: Request): Option[Boolean] = {
    val rules = request.action match {
      case Read => readPolicies
      case Write => writePolicies
      case Update => updatePolicies
      case Remove => deletePolicies
    }

    val target = Database.getRecordById(request.target)

    target.map { record =>
//      rules.map { rule =>
      rules.exists { rule =>
        rule.compute(request.subject, record)
      }
//      }.foldLeft(false)(_ || _)
    }
  }

}
