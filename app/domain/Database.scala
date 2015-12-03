package domain

import java.time.LocalDateTime

object Database {
  private var physicianCount = 0l
  private var physicians = Map.empty[Long, Physician]

  private var patientCount = 0l
  private var patients = Map.empty[Long, Patient]

  private var recordCount = 0l
  private var records = Map.empty[Long, MedicalRecord]

  def addPhysician(name: String, speciality: Speciality): Physician = {
    val id = physicianCount
    physicianCount += 1

    val physician = Physician(name, speciality, id = id)
    physicians += id -> physician

    physician
  }

  def addPatient(name: String, birthday: LocalDateTime): Patient = {
    val id = patientCount
    patientCount += 1

    val patient = Patient(name, birthday, id = id)
    patients += id -> patient

    patient
  }

  def addMedicalRecord(creator: Physician, kind: Speciality, patient: Patient, content: String): MedicalRecord = {
    val id = recordCount
    recordCount += 1
    val record = MedicalRecord(creator, kind, content = content, id = id)
    records += id -> record
    patients(patient.id).medicalRecords += record
    record
  }

  def getMedicalRecord(id: Long): Option[MedicalRecord] = records.get(id)

  def updateMedicalRecord(id: Long, content: String): Boolean = records.get(id) match {
    case Some(record) =>
      records += id -> record.copy(content = content)
      true
    case _ =>
      false
  }
}
