package domain

import java.time.LocalDateTime

object Database {
  private var physicianCount = 0l
  private var physicians = Map.empty[Long, Physician]

  private var patientCount = 0l
  private var patients = Map.empty[Long, Patient]

  private var recordCount = 0l
  private var records = Map.empty[Long, MedicalRecord]

  def addDoctor(name: String, speciality: Speciality): Physician = {
    physicianCount += 1

    val id = physicianCount
    val physician = Physician(name, speciality, id)
    physicians += id -> physician
    physician
  }

  def addPatient(name: String, birthday: LocalDateTime): Patient = {
    patientCount += 1

    val id = patientCount
    val patient = Patient(name, birthday, id = id)
    patients += id -> patient
    patient
  }

  def addMedicalRecord(creator: Physician, kind: Speciality, patient: Patient, content: String): MedicalRecord = {
    recordCount += 1

    val id = recordCount
    val record = MedicalRecord(creator, kind, content = content, id = id)
    records += id -> record
    patients(patient.id).medicalRecords += record
    record
  }
}
