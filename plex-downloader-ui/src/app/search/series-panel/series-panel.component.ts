import {Component, Inject, Input, OnInit, Optional} from '@angular/core';
import {Directory} from "../../models/directory.model";
import {LibraryService} from "../../_service/library.service";
import {Video} from "../../models/video.model";
import {Constants} from "../../util/constants";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

export class ModalData {
  video: Video;
  show: Directory;
}

@Component({
  selector: 'app-series-panel',
  templateUrl: './series-panel.component.html',
  styleUrls: ['./series-panel.component.css']
})
export class SeriesPanelComponent implements OnInit {

  @Input() show: Directory;
  video: Video;

  private mediaPhotoUrl: string;
  private seriesPhotoUrl: string;

  seasons = new Array<Directory>();

  constructor(private libraryService: LibraryService,
              public dialogRef: MatDialogRef<SeriesPanelComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: ModalData) {}

  ngOnInit() {
    if (this.data.video != null) {
      this.video = this.data.video;
      this.resolvePosterURL(this.video);
    } else {
      this.show = this.data.show;
    }
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

        this.resolveSeriesPosterURL(this.show);
      });
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /**
   *
   * @param video
   */
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
    this.onNoClick();
    //window.open(url);
  }

  resolveBannerURL(video: Video): string {
    //TODO have server side process URL example: http://{SERVER_IP}:{PORT}/photo/:/transcode?url=/library/metadata/13686/thumb/1576691662&width=500&height=500&X-Plex-Token=qraeKhWxgqinH2ysa44W
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let thumb = video.type === 'movie' ? video.art : video.grandparentArt;
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }

  resolvePosterURL(video: Video) {

    let thumb = video.type === 'movie' ? video.thumb : video.grandparentThumb;

    this.libraryService.retrievePhotoFromPlexServer(thumb).subscribe((photoUrl: string) => {
      this.mediaPhotoUrl = photoUrl;
    }, () => {
      console.log('Error loading photo url');
    });

  }

  resolveSeriesPosterURL(directory: Directory) {
    let thumb = directory.thumb;
    this.libraryService.retrievePhotoFromPlexServer(thumb).subscribe((photoUrl: string) => {
      // return photoUrl;
      this.seriesPhotoUrl = photoUrl
    }, () => {
      console.log('Error loading photo url');
    });
  }

}
