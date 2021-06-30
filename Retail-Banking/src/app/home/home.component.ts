import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  disp="none";
  data!:any;
  url='';
  constructor() { }

  ngOnInit(): void {
    this.url='http://localhost:4200/employee'
  }

  onCustomer(data:string){
    console.warn(data);
    console.warn("aagya");
    if(data=='create')
    this.url="http://localhost:4200/register";
    if(data=='view')
    this.url="http://localhost:4200/view"
    if(data=='deposit-withdraw')
    this.url="http://localhost:4200/deposit-withdraw"
    if(data=='statement')
    this.url="http://localhost:4200/statement"
  }
  onUpdate(data:string){
    console.warn(data);
    console.warn("aagya");
    this.url="http://localhost:4200/update/"+data;
    console.warn(this.url);
    
  }

  deleteCustomer(data:string){
    console.warn(data);
    this.data=data;
    console.warn(this.data);
    
  }
}
