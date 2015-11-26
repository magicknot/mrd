package domain.policies

sealed trait Rule

case object Permit extends Rule
case object Deny extends Rule
