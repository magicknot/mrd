package domain

object Database {
  var doctors = Map.empty[Long, Doctor]
  var patients = Map.empty[Long, Patient]
  var records = Map.empty[Long, MedicalRecord]

  def getRecordById(id: Long): Option[MedicalRecord] = records.get(id)
}
