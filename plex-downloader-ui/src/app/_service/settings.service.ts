import { Injectable } from '@angular/core';
import {Constants} from "../util/constants";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AlertifyService} from "./alertify.service";
import {map} from "rxjs/operators";
import {About} from "../models/settings/about.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  baseUrl = Constants.PLEX_DOWNLOADER_BASE_URL + '/settings';

  constructor(
    private http: HttpClient,
    private router: Router,
    private alertify: AlertifyService) {}



  retrieveAppInfo() : Observable<About> {
    return this.http.get(this.baseUrl + '/about')
      .pipe(
        map((about: any) => {
          return about;
        })
      );
  }
}
