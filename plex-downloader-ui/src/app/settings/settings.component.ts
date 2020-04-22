import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {SettingsService} from "../_service/settings.service";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  isAdmin  = false;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private settingsService: SettingsService,
              private componentMessagingService: ComponentMessagingService) { }


  ngOnInit() {
    this.validateFullSettingsAccess();
  }

  validateFullSettingsAccess() {
    this.settingsService.validateFullSettingsAccess().subscribe(access => {
      this.isAdmin = access.status === 200;
    }, error => console.log('Error getting settings access: ' + error));
  }
}
