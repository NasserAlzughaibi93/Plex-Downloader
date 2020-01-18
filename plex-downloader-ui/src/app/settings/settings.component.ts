import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {SettingsService} from "../_service/settings.service";
import {About} from "../models/settings/about.model";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(private router: Router,
              private alertify: AlertifyService,
              private settingsService: SettingsService,
              private componentMessagingService: ComponentMessagingService) { }

  ngOnInit() {}

}
