package com.simpleproject.hospital_management.controller;

import com.simpleproject.hospital_management.model.Doctor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/doctor/apis")
public class DoctorController {
    //here we use Hashmap for database operations(CURD)
    HashMap<Integer, Doctor> doctorHashMap = new HashMap<>();

    // input to the spring boot is taken inthe form of json ->java object notation
    //@RequestBody -> takes the request or input from thr ui/postman and assign it to object
    @PostMapping("/save")
    public String saveDoctor(@RequestBody Doctor doctorRequest){
        //take the request and add it inside hashmap
        doctorHashMap.put(doctorRequest.getId(),doctorRequest);
        return "Doctor Saved Successfully";
    }

    @GetMapping("/findAll")
    public HashMap<Integer,Doctor> getAllDoctors(){
        return doctorHashMap;
    }

    //@PathVariable - it is the input sent in url path(endpoint)
    @GetMapping("/find/{doctorid}")
    public Doctor getDoctorById(@PathVariable("doctorid") int doctorId){
         Doctor doctor =doctorHashMap.get(doctorId);
         return doctor;
    }

    //@RequestParam - takes the input in the form of a request parameter as a query
    @GetMapping("/findByName")
    public Doctor getDoctorByName(@RequestParam("/doctorName") String doctorName){
        for(Doctor doctor : doctorHashMap.values()){
            if(doctor.getName().equals(doctorName)){
                return doctor;
            }
        }
        return null;
    }
   @GetMapping("/findbySpecailization")
    public List<Doctor> specialization(@RequestParam String specialization){
        List<Doctor> doctorList = new ArrayList<>();
        for(Doctor doctor :doctorHashMap.values()){
            if(doctor.getSpecialization().equalsIgnoreCase(specialization)){
                doctorList.add(doctor);
            }
        }
        return doctorList;
    }

    @GetMapping("/findByAgeandSpecialization")
    public List<Doctor> getDoctorByAgeAndSpecialization(@RequestParam int age,@RequestParam String specialization){
        List<Doctor> doctorList=new ArrayList<>();
        for(Doctor doctor : doctorHashMap.values()){
            if(doctor.getAge() ==age && doctor.getSpecialization().equals(specialization)){
                doctorList.add(doctor);
            }
        }
        return doctorList;
    }

    @GetMapping("/findByAgeOrSpecialization")
    public List<Doctor> getDoctorByAgeOrSpecialization(@RequestParam(required = false) int age,@RequestParam(required =false) String specialization) {
        List<Doctor> doctorList = new ArrayList<>();
        for (Doctor doctor : doctorHashMap.values()) {
            if (age != 0 && doctor.getAge() == age) {
                doctorList.add(doctor);
            }
            else if(specialization!= null && doctor.getSpecialization().equals(specialization)){
                doctorList.add(doctor);
            }
        }
        return doctorList;
    }

    //Delete API
    @DeleteMapping("/delete/{id}")
    public String deleteDoctorById(@PathVariable int id){
        doctorHashMap.remove(id);
        return "Doctor with id : "+id+" is deleted successfully";
    }

    @PutMapping("/Update/{id}")
    public String UpdateDoctor(@PathVariable int id ,@RequestBody Doctor doctorRequest){
        //check if docotr id is present
        //if present update
        //else not
        Doctor doctor = doctorHashMap.get(id);
        if(doctor!= null){
            doctorHashMap.put(id,doctorRequest);
            return "Doctor updated Successfully";
        }else{
            return "Doctor not found with given id : "+id;
        }

    }
}
