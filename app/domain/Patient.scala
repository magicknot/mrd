package domain

import java.time.LocalDateTime

case class Patient(name: String, birthday: LocalDateTime, var medicalRecords: Set[MedicalRecord] = Set.empty, id: Long)