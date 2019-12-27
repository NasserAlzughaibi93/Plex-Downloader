import { Component, OnInit } from '@angular/core';
import {LoginService} from '../_service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};

  constructor(private loginService: LoginService) { }

  ngOnInit() {
  }

  login() {
    this.loginService.login(this.model).subscribe(() => {
      console.log('worked');
    }, () => {
      console.log('Error');
    });
  }
}
