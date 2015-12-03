package domain

import java.time.LocalDateTime

case class MedicalRecord(creator: Physician, kind: Speciality, creationDate: LocalDateTime = LocalDateTime.now(),
                         content: String, id: Long)
