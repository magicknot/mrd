package domain

sealed trait Speciality

case object AllergyAndImmunology extends Speciality
case object Cardiology extends Speciality
case object InternalMedicine extends Speciality
case object Ophthalmology extends Speciality
case object Neurology extends Speciality
