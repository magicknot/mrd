package domain

import java.time.LocalDateTime

case class MedicalRecord(creator: Physician,
                         department: Department,
                         creationDate: LocalDateTime = LocalDateTime.now(),
                         lastChange: LocalDateTime = LocalDateTime.now(),
                         content: String,
                         id: Long)
