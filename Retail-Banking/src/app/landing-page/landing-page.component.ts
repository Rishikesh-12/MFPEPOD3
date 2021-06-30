import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {
  disp="none";
  url='';
  constructor() { }

  ngOnInit(): void {
  }

  onLogin(data:string){
    console.warn(data);
    console.warn("aagya");
    this.disp="block"
    if(data=='user')
    this.url="http://localhost:4200/login";
    else
    this.url="http://localhost:4200/admin";
  }
}
