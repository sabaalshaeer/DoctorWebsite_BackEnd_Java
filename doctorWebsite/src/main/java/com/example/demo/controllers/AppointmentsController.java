package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.models.Appointment;

@RestController 
@RequestMapping("/appointments")
//CORS Error:Cross origin request
//by 'orgin' we mean server
//browser is connected to localhost:4200
//the app on 4200 is making a request to localhost:8080
//browser to make sure that the origin at 8080 is ok with sending data to the origin at 4200
//add a allowed-origin to all responses with a * wildcard
@CrossOrigin 
public class AppointmentsController {
	
	private Long nextAppointmentId = 0L;
	private ArrayList<Appointment> appointments = new ArrayList<>();
	
	@PostMapping
	//the body of the HTTP requests must be formatted
	//as a Json string that Json string must match
	//an appointment object ,will be converted to appointment object.
	//None of that happens in either. We throw an error or
	//this method would not be called at all.
	public void addNewAppointment(@RequestBody Appointment appointment) {
		//force the id is unique 
		for(Appointment existingAppointment : appointments) {
			if (existingAppointment.id.equals(appointment.id))
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
		}
		appointment.id = nextAppointmentId++;
		appointments.add(appointment);
	}
	
	//get appointments by patientId and doctorId
	@GetMapping
	public Iterable<Appointment> getAppointments(@RequestParam(required = false) Long patientId,
			     								@RequestParam(required = false) Long doctorId){
		//FE: this.http.get<Appointment[]>(`http://localhost:3000/appointments?patientId=${this.userId}`)
		if(patientId != null) {
			//final : means I am going to initialize the variable maching to new ArrayList<Appointment>()  and I am never going to set it again, I can still call methods on it 
			//but as far as this matching goes I could not then reassign it to be something else like matching = null;  
			final var matching = new ArrayList<Appointment>();//empty arrylist of appointment for now called it matching(temporary list)
			//I run throght the entire list of appointments, if the patientId match then I add it to this temporary List
			//by the end I return the temporary list
			for (Appointment appointment : appointments)
				//appointment.patientId.equals(patientId) might throw becuase I did not check if appointment.patientId is null or not
				if(patientId.equals(appointment.patientId)) {
					matching.add(appointment);
				}
			return matching;
		}
		
		if(doctorId != null) {
			final var matching = new ArrayList<Appointment>();
			for (Appointment appointment : appointments)
				if(doctorId.equals(appointment.doctorId)) {
					matching.add(appointment);
				}
			return matching;
		}

		//FE: this.http.get<Appointment[]>('http://localhost:3000/appointments')
		return appointments;
	}
	
	//FE: this.http.delete(`http://localhost:3000/appointments/${id}`)//id is a path variable
	@DeleteMapping("/{id}") // I want spring know that whatever valueof this path variable ,I want this go inside of argument of the deleteById method 
	public void deleteById(@PathVariable Long id) {
		for( var appointment: appointments) {
			if(appointment.id.equals(id)) {
				appointments.remove(appointment);
			return;
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	
	//FE: FE: this.http.put(`http://localhost:3000/appointments/${appointment.id}`)
	@PutMapping("/{id}")
	public void modifyAppointment(@RequestBody  Long id, Appointment appointment) {
		//check whatever appointment was passed in and the body does not have the matching id
		if(!id.equals(appointment.id))
		 throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		//find id with this list of Appintments
		for(var existingAppointment: appointments) {
			if(id.equals(existingAppointment.id)) {
				//I need to modify an exist appointment, I will remove it and add it again(inefficient way)
				appointments.remove(existingAppointment);
				appointments.add(appointment);
				//an efficient way) but this way will modify the appointment but not call it back asynch to the list to display in the frontend
//				existingAppointment.doctorId =appointment.doctorId;
//				existingAppointment.patientId = appointment.patientId;
//				existingAppointment.date = appointment.date;
//				existingAppointment.slot = appointment.slot;
				return;
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}
	
	
	//3 different way to pass information from FE to the BE
	// .PathVariable , URL variable ( when we use RequestParm) and as a Body ( RequestBody)

}
