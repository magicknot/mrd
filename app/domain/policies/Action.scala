package domain.policies

sealed trait Action

case object Write extends Action
case object Read extends Action
case object Remove extends Action