import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {LoginService} from '../_service/login.service';
import {AlertifyService} from '../_service/alertify.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService,
              private router: Router,
              private alertify: AlertifyService) {}

  canActivate(): boolean {
    /*if (this.authService.loggedIn()) {
      return true;
    }*/

    this.alertify.error('GTFO please');

    this.router.navigate(['/login']);

    return false;
  }
}
