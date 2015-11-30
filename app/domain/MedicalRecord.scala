package domain

import java.time.LocalDateTime

case class MedicalRecord(creator: Doctor, kind: Speciality, creationDate: LocalDateTime, content: String)
