import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {SettingsService} from "../_service/settings.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit, OnDestroy {

  // used unsubscribe from observable
  private _destroy$: Subject<boolean> = new Subject();

  isAdmin  = false;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private settingsService: SettingsService,
              private componentMessagingService: ComponentMessagingService) { }


  ngOnInit() {
    this.validateFullSettingsAccess();
  }

  validateFullSettingsAccess() {
    this.settingsService.validateFullSettingsAccess().pipe(
      takeUntil(this._destroy$)
    ).subscribe(access => {
        this.isAdmin = access.status === 200;
    }, error => console.log('Error getting settings access: ' + error));
  }

  ngOnDestroy() {
    this._destroy$.next(true);
    this._destroy$.unsubscribe();
  }

}
