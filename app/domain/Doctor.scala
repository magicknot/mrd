package domain

case class Doctor(name: String, var specialities: Set[Speciality] = Set.empty)
