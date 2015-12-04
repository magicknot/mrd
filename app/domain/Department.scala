package domain

sealed trait Department

case object AllergyAndImmunology extends Department
case object Cardiology extends Department
case object InternalMedicine extends Department
case object Ophthalmology extends Department
case object Neurology extends Department
