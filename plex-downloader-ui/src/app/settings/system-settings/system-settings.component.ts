/* tslint:disable */
import {Component, OnInit} from '@angular/core';
import {SettingsService} from "../../_service/settings.service";
import {SystemSettings} from "../../models/settings/system.settings.model";
import {SystemSettingsDTO} from "../../models/settings/system.settings.dto.model";
import {LoggingLevel} from "../../models/settings/logging.level.model";
import {AlertifyService} from "../../_service/alertify.service";

@Component({
  selector: 'app-system-settings',
  templateUrl: './system-settings.component.html',
  styleUrls: ['./system-settings.component.css']
})
export class SystemSettingsComponent implements OnInit {

  systemSettingsToSave = {} as SystemSettingsDTO;
  loggingLevel: LoggingLevel;

  constructor(private settingsService: SettingsService,
              private alertify: AlertifyService) { }

  ngOnInit() {
    this.retrieveSystemSettings();
  }

  retrieveSystemSettings() {
    this.settingsService.retrieveSystemSettings().subscribe((systemSettings: SystemSettings) => {
      if (null != systemSettings) {
        this.resolveSystemSettingsDTO(systemSettings);
      }
    });
  }

  getLogginglevelKeys() {
    let keys = Object.keys(LoggingLevel);
    return keys.splice(keys.length/2);
  }

  saveSystemSettings() {
    this.settingsService.saveSystemSettings(this.systemSettingsToSave).subscribe((systemSettings: SystemSettings) => {
      if (null != systemSettings) {
        this.resolveSystemSettingsDTO(systemSettings);
        this.alertify.success('System settings saved.');
      }
    });
  }

  private resolveSystemSettingsDTO(systemSettings: SystemSettings) {
    this.systemSettingsToSave.loggingLevel = systemSettings.loggingLevel;
    this.systemSettingsToSave.downloadIntervalInMinutes = systemSettings.downloadIntervalInMinutes;
    this.systemSettingsToSave.maxDownloadsPerUser = systemSettings.maxDownloadsPerUser;
  }
}
