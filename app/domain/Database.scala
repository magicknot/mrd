package domain

import java.time.{LocalTime, LocalDateTime}

object Database {
  private var physicianCount = 0l
  private var physicians = Map.empty[Long, Physician]

  private var nurseCount = 0l
  private var nurses = Map.empty[Long, Nurse]

  private var patientCount = 0l
  private var patients = Map.empty[Long, Patient]

  private var recordCount = 0l
  private var records = Map.empty[Long, MedicalRecord]

  def addPhysician(name: String, speciality: Department): Physician = {
    val id = physicianCount
    physicianCount += 1

    val physician = Physician(name, speciality, id = id)
    physicians += id -> physician

    physician
  }

  def addNurse(name: String, department: Department, begin: LocalTime, end: LocalTime): Nurse = {
    val id = nurseCount
    nurseCount += 1

    val nurse = Nurse(name, begin, end, department, id)
    nurses += id -> nurse

    nurse
  }

  def addPatient(name: String, birthday: LocalDateTime): Patient = {
    val id = patientCount
    patientCount += 1

    val patient = Patient(name, birthday, id = id)
    patients += id -> patient

    patient
  }

  def addMedicalRecord(creator: Physician, kind: Department, patient: Patient, content: String): MedicalRecord = {
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
