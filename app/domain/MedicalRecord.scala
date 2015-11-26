package domain

case class MedicalRecord(creator: Doctor, kind: Speciality, content: String)
