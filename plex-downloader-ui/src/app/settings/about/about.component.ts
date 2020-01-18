import { Component, OnInit } from '@angular/core';
import {SettingsService} from "../../_service/settings.service";
import {About} from "../../models/settings/about.model";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  about: About;

  constructor(private settingsService: SettingsService) { }

  ngOnInit() {
    this.retrieveAppInfo();
  }

  retrieveAppInfo() {
    this.settingsService.retrieveAppInfo().subscribe(about => {
      this.about = about;
    });
  }

}
