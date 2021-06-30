import { Component, OnInit } from '@angular/core';
import { Customer } from '../customer';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  customer={
    name:'sdf',
    address:'sdf',
    dob:'sdf',
    panNo:'sdf',
    email:'sdf',
    phone:'sdf'
  };
  constructor() { }

  ngOnInit(): void {
  }
  addCustomer(data:any){
    console.warn(data);
    let date=document.getElementById("dob");
    this.customer.address=data.address;
    this.generateDate(data.dob);
    this.customer.name=data.fname+" "+data.lname;
    this.customer.email=data.email;
    this.customer.panNo=data.panNo;
    this.customer.phone=data.phone;
    console.warn(this.customer);
    
  }
  generateDate(date:any){
    this.customer.dob=new Date(date.getTime()).toLocaleDateString("en-us");
    
  }
}
