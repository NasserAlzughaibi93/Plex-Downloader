import {Component, OnDestroy, OnInit} from '@angular/core';
import {SettingsService} from "../../_service/settings.service";
import {About} from "../../models/settings/about.model";
import {LoadingScreenService} from "../../_service/loading.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit, OnDestroy {

  // used unsubscribe from observable
  private _destroy$: Subject<boolean> = new Subject();

  about: About;

  constructor(private settingsService: SettingsService,
              private loadingScreenService: LoadingScreenService) {
  }

  ngOnInit() {
    // this.loadingScreenService.stopLoading();

    this.retrieveAppInfo();

  }

  retrieveAppInfo() {
    this.settingsService.retrieveAppInfo().pipe(
      takeUntil(this._destroy$)
    ).subscribe(about => {
        this.about = about;
        // this.loadingScreenService.stopLoading();
    });
  }

  ngOnDestroy() {
    this._destroy$.next(true);
    this._destroy$.unsubscribe();
  }

}
