import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-employee-home',
  templateUrl: './employee-home.component.html',
  styleUrls: ['./employee-home.component.css']
})
export class EmployeeHomeComponent implements OnInit {
  disp="none";
  url='';
  constructor(private route:Router) { }

  ngOnInit(): void {
  }

  onLogin(data:string){
    console.warn(data);
    console.warn("aagya");
    this.route.navigate(["register"]);
  }

}
