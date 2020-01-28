import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnDestroy, OnInit} from '@angular/core';
import {LoadingScreenService} from "../../_service/loading.service";
import {Subscription} from "rxjs";
import { debounceTime } from "rxjs/operators";

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements AfterViewInit, OnDestroy {

  debounceTime: number = 0;
  loading: unknown = false;
  loadingSubscription: Subscription;

  constructor(private loadingScreenService: LoadingScreenService,
              private _elmRef: ElementRef,
              private _changeDetectorRef: ChangeDetectorRef) {
  }

  /**
   * Add <app-loading></app-loading> under all navbar imports to add full page load bar.
   */
  ngAfterViewInit(): void {
    this._elmRef.nativeElement.style.display = 'none';
    this.loadingSubscription = this.loadingScreenService.loadingStatus.pipe(debounceTime(this.debounceTime)).subscribe(
      (status: boolean) => {
        this._elmRef.nativeElement.style.display = status ? 'block' : 'none';
        this._changeDetectorRef.detectChanges();
      }
    );
  }

  ngOnDestroy() {
    this.loadingSubscription.unsubscribe();
  }

}
