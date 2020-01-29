import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {AlertifyService} from '../_service/alertify.service';
import {Constants} from "../util/constants";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService,
              private router: Router,
              private alertify: AlertifyService) {}

  canActivate(): boolean {

    if (this.loginService.isLoggedIn()) {
      return true;
    }

    console.log('Error, not signed in. Returning back to login page.');
    this.alertify.error("Please sign in first");
    this.router.navigate(['/']);

    return true;
  }
}
