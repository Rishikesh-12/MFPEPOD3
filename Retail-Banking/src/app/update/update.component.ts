import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {
  custid!:string;
  data={
    fname:"Prateek",
    lname:"Jaiswal",
    dob:new Date("2020-12-03"),
    email:"jaiswalprateek231@gmail.com",
    phone:"8448635400",
    panNo:"ASDF123456",
    address:"C 84/11/12 Ram Gopal Vidyanth Road, Maqbool Ganj, Lucknow"
  }
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.custid=this.route.snapshot.params.id;
    console.warn(this.custid);
    
  }
  updateCustomer(data:any){
    console.warn(data);
    this.data=data;
    console.warn(this.data);
    
  }
}
