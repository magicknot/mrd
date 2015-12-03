package domain

case class Physician(name: String, var speciality: Speciality, treatingPatients: Set[Patient] = Set.empty, id: Long)
