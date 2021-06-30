import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-customer-home',
  templateUrl: './customer-home.component.html',
  styleUrls: ['./customer-home.component.css']
})
export class CustomerHomeComponent implements OnInit {
  data="asds";
  accounts=[
    {
      custid:"1002",
      name:"Prateek Jaiswal",
      accNo:"147310521000",
      balance:7000.0
    },
    {
      custid:"1002",
      name:"Prateek Jaiswal",
      accNo:"409211087501",
      balance:3000.0
    }
  ]
  cust!:string;
  constructor() { }

  ngOnInit(): void {
  }
  
  deleteCustomer(data:string){
    let data1=document.getElementById("custid");
    console.warn(data);
    console.warn(this.data);
    
  }
}
