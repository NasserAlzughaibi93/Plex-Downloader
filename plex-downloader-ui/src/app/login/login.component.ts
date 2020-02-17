import {Component, OnDestroy, OnInit} from '@angular/core';
import {LoginService} from '../_service/login.service';
import {Constants} from "../util/constants";
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  model: any = {};

  isBasicLogin = false;

  oAuthPinTimer: any;

  constructor(private loginService: LoginService,
              private router: Router,
              private alertify: AlertifyService) {
    let authToken = localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    if (authToken != null && authToken.trim().length != 0) {
      authToken = null;
      this.router.navigate(['/home'])
    }
  }

  ngOnInit() {}

  basicLogin() {
    this.loginService.login(this.model).subscribe(() => {
      console.log('worked');
      this.router.navigate(['/home']);
    }, error => {
      console.log('Error during a basic login: ' + error);
    });
  }

  oAuthLogin() {
    const oAuthWindow = window.open(window.location.toString(), "_blank", `toolbar=0,
        location=0,
        status=0,
        menubar=0,
        scrollbars=1,
        resizable=1,
        width=500,
        height=500`);

    this.loginService.retrieveOAuthLoginPinAndUrl().subscribe(plexPin => {
      console.log("Retrieved Pin: " + plexPin.id);
      console.log("Retrieved resolved URI: " + plexPin.resolvedUri);
      oAuthWindow!.location.replace(plexPin.resolvedUri);

      this.oAuthPinTimer = setInterval(() => {
        this.alertify.notification("Authenticating", "Loading... Please Wait");
        //this.getPinResult(x.pinId);
        this.loginService.retrieveOAuthPinResults(plexPin.id).subscribe(user => {
          if (user.jwtToken != null && user.jwtToken.trim().length != 0) {
            localStorage.setItem(Constants.PLEX_AUTH_TOKEN, user.jwtToken);
            localStorage.setItem(Constants.PLEX_USER_ICON, user.thumb);
            this.ngOnDestroy();
            this.router.navigate(['/home']);
          }
        }, error => {
          console.log("Error while logging in (OAuth): " + error);
          this.router.navigate(['/']);
        })
      }, 10000);

    }, error => {
      console.log("Error: " + error);
    });
  }

  ngOnDestroy() {
    clearInterval(this.oAuthPinTimer);
  }
}
