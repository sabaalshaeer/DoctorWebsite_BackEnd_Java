package com.example.demo.models;

import java.util.Date;
//definition of Appointment
public class Appointment {
	public Long id;
	public Long doctorId;
	public Long patientId;
	public Date date;
	public int slot;
	
	public Appointment(Long id, Long doctorId, Long patientId, Date date, int slot) {
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.date = date;
		this.slot = slot;
	}
}
