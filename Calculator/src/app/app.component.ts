import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

//function to check isnumeric or not using regex
function isNumber(n:string):boolean { return /^[-]?([0-9]+\.?[0-9]*|\.)$/.test(n); }

//function to check is the expression in included operation or not using regex
function limited_expression(expression:string):boolean {return /^[-]?([0-9]+\.?[0-9]*)[-+*/^]+[-]?([0-9]+\.?[0-9]*)$/.test(expression);}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  //variables initialization.
  title = 'Calculator';
  public output_flag:boolean = false;
  public result:string = "";

  constructor(private http:HttpClient){}

  //function that display the entered symbol on the screen and make some assertions on the expression which is displayed on the screen.
  display(value:string):void
  {
    if(!limited_expression(this.result) || isNumber(value)){
      if((this.output_flag && (isNumber(value)) ) || (this.result == "E")){
        this.result = value;
      }else{
        this.result += value;
      }
      this.output_flag = false;

    }else if(limited_expression(this.result) && !isNumber(value)){
      this.past_calculate(value)

    }

  }

  //function that change the sign of the displayed number.
  change_sign():void
  {
    if(isNumber(this.result) && (Number(this.result) > 0)){
      this.result = "-"+this.result;
    }else if (isNumber(this.result) && (Number(this.result) < 0)) {
      this.result = this.result.slice(1,);
    } else {

    }
  }

  //function that evaluates the expression when the user press '=' and return the result after posting it to a local API.
  calculate():void
  {
    this.http.post('http://localhost:8080/Calculator', this.result, {
      headers: new HttpHeaders({
        'Content-Type': 'text/plane'
      })
      ,responseType: 'text'
    }).subscribe((response) => this.result = response,(error)=>console.log(error));
      this.output_flag = true;
  }

  //function that evaluates the expression when the user enter more than one operation on the screen,
  //and return the result with the extra operation.
  past_calculate(value:string):void
  {
    this.http.post('http://localhost:8080/Calculator', this.result, {
      headers: new HttpHeaders({
        'Content-Type': 'text/plane'
      })
      ,responseType: 'text'
    }).subscribe((response) => this.result = response + value,(error)=>console.log(error));
  }
  //function that clear the display
  clearAll():void
  {
      this.result = "";
  }
  //function that clear one symbol from the screen
  clearElement():void
  {
    if(!this.output_flag){
      var x = this.result;
      this.result = x.slice(0,-1);
    }
  }


}
