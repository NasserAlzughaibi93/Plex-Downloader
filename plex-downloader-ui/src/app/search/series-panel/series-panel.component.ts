import {Component, Inject, Input, OnInit, Optional} from '@angular/core';
import {Directory} from "../../models/directory.model";
import {LibraryService} from "../../_service/library.service";
import {Video} from "../../models/video.model";
import {Constants} from "../../util/constants";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-series-panel',
  templateUrl: './series-panel.component.html',
  styleUrls: ['./series-panel.component.css']
})
export class SeriesPanelComponent implements OnInit {

  @Input() show: Directory;

  seasons = new Array<Directory>();

  constructor(private libraryService: LibraryService,
              public dialogRef: MatDialogRef<SeriesPanelComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.show = this.data;
    if (this.show) {
      console.log("calling for more");
      this.libraryService.retrieveMediaMetaDataChildren(this.show.key).subscribe((directories) => {

        this.seasons = directories;

        console.log('testing, season sizes: ' + this.seasons.length);

        // let indexToRemove  = this.seasons.findIndex(season => season.title === 'All episodes');
        //
        // if (indexToRemove)
        //   this.seasons.splice(indexToRemove, 1);

        this.seasons.forEach(season => {
          console.log('getting season no.' + season.title);
          this.libraryService.retrieveMediaMetaData(season.key).subscribe((videos) => {
            season.videos = videos;
          });
        });

      });
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  startDownloadingMedia(video: Video) {

    this.libraryService.retrieveMediaDownloadLink(video).subscribe(downloadLink => {
      this.beginDownload(downloadLink);
    });

  }

  beginDownload(url: string) {
    var link = document.createElement("a");
    link.download = "a";
    link.href = url;
    document.body.appendChild(link);
    link.click();
    //window.open(url);
  }

}
