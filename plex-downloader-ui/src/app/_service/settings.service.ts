import { Injectable } from '@angular/core';
import {Constants} from "../util/constants";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AlertifyService} from "./alertify.service";
import {map} from "rxjs/operators";
import {About} from "../models/settings/about.model";
import {Observable} from "rxjs";
import {SystemSettings} from "../models/settings/system.settings.model";
import {SystemSettingsDTO} from "../models/settings/system.settings.dto.model";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  baseUrl = Constants.PLEX_DOWNLOADER_BASE_URL + '/settings';

  constructor(
    private http: HttpClient,
    private router: Router,
    private alertify: AlertifyService) {}


  /**
   * Retrieves the application servers information
   */
  retrieveAppInfo() : Observable<About> {
    return this.http.get(this.baseUrl + '/about')
      .pipe(
        map((about: any) => {
          return about;
        })
      );
  }

  /**
   * Checks if the user is a server owner.
   */
  validateFullSettingsAccess() : Observable<any> {
    return this.http.get(this.baseUrl, { observe: 'response' }).pipe(
      map(access => {
        console.log(access.status);
        return access;
      })
    );
  }

  /**
   * Retrieves the system settings for the currently logged in user if they have access.
   */
  retrieveSystemSettings(): Observable<SystemSettings> {
    return this.http.get(this.baseUrl + '/system').pipe(
      map((systemSettings: SystemSettings) => {
        return systemSettings;
      })
    );
  }

  /**
   * Save the system settings for the user.
   * @param systemSettingsDTO
   */
  saveSystemSettings(systemSettingsDTO: SystemSettingsDTO) : Observable<SystemSettings> {
    return this.http.post(this.baseUrl + '/system', systemSettingsDTO).pipe(
      map((systemSettings: SystemSettings) => {
        return systemSettings;
      })
    );
  }
}
