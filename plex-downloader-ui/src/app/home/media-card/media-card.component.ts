import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Video} from "../../models/video.model";
import {Constants} from "../../util/constants";
import {Router} from "@angular/router";
import {AlertifyService} from "../../_service/alertify.service";
import {LibraryService} from "../../_service/library.service";
import {ComponentMessagingService} from "../../_service/component-messaging.service";
import {Directory} from "../../models/directory.model";
import {SeriesPanelComponent} from "../../search/series-panel/series-panel.component";
import {MatDialog} from "@angular/material/dialog";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-media-card',
  templateUrl: './media-card.component.html',
  styleUrls: ['./media-card.component.scss']
})
export class MediaCardComponent implements OnInit, OnDestroy {

  // used unsubscribe from observable
  private _destroy$: Subject<boolean> = new Subject();

  @Input() video: Video;
  @Input() show: Directory;

  mobile = false;
  // static count = 0;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    if (window.screen.width < 768) {
      this.mobile = true;
    }
  }

  openDialog(video: Video): void {
    console.log("video example: " + video.title);
    const dialogRef = this.dialog.open(SeriesPanelComponent, {
      maxHeight: '70%',
      maxWidth: '70%',
      data: {video: video}
    });

    dialogRef.afterClosed().pipe(
      takeUntil(this._destroy$)
    ).subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openShowDialog(show: Directory): void {
    console.log("show example: " + show.title);
    const dialogRef = this.dialog.open(SeriesPanelComponent, {
      width: '60%',
      data: {show: show}
    });

    dialogRef.afterClosed().pipe(
      takeUntil(this._destroy$)
    ).subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  ngOnDestroy() {
    this._destroy$.next(true);
    this._destroy$.unsubscribe();
  }

}
