import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent implements OnInit {
  account=[
    {
      custid:"1002",
      name:"Prateek Jaiswal",
      accNo:"147310521000",
      balance:7000.0,
      type:'Savings'
    },
    {
      custid:"1002",
      name:"Prateek Jaiswal",
      accNo:"409211087501",
      balance:3000.0,
      type:'Current'
    }
  ];
  accounts:any;
  constructor() { }

  ngOnInit(): void {
  }
  onSearch(data:string){
    console.warn(data);
    this.accounts=this.account;
  }

}
