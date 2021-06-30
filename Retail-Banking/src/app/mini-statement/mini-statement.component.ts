import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mini-statement',
  templateUrl: './mini-statement.component.html',
  styleUrls: ['./mini-statement.component.css']
})
export class MiniStatementComponent implements OnInit {
  disp='none';
  statement=[
    {
      date:'29/05/2021',
      narration:"Deposit was successfull",
      reference:"HSNK1234567",
      amount:2000.0,
      withdraw:0.0,
      depoit:2000.0,
      balance:9000.0
    },
    {
      date:'29/06/2021',
      narration:"Withdrawal was successfull",
      reference:"HHFD489567",
      amount:0.0,
      withdraw:2000.0,
      depoit:2000.0,
      balance:7000.0
    }
  ];
  statements:any;
  constructor() { }

  ngOnInit(): void {
  }
  onSearch(data:string){
    console.warn(data);
    this.statements=this.statement;
    this.disp='block';
  }

}
