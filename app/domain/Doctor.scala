package domain

case class Doctor(name: String) {
  var specialities = Set.empty[Speciality]
}
