import {Component, Inject, Input, OnInit, Optional} from '@angular/core';
import {Directory} from '../../models/directory.model';
import {LibraryService} from '../../_service/library.service';
import {Video} from '../../models/video.model';
import {Constants} from '../../util/constants';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {AlertifyService} from '../../_service/alertify.service';
import {DownloadRequest, MediaType} from '../../models/download-request.model';
import {Track} from '../../models/track.model';

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

  mediaPhotoUrl: string;
  seriesPhotoUrl: string;

  seasons = new Array<Directory>();

  constructor(private libraryService: LibraryService,
              public alertify: AlertifyService,
              public dialogRef: MatDialogRef<SeriesPanelComponent>,
              @Optional() @Inject(MAT_DIALOG_DATA) public data: ModalData) {}

  ngOnInit() {
    if (this.data.video != null) {
      this.video = this.data.video;
      console.log('Call 1')
      this.resolvePosterURL(this.video);
    } else {
      this.show = this.data.show;
    }
    if (this.show) {
      console.log('calling for more');
      this.libraryService.retrieveMediaMetaDataChildren(this.show.key).subscribe((mediaContainer) => {

        this.seasons = mediaContainer.directory;

        console.log('testing, season sizes: ' + this.seasons.length);

        this.seasons.forEach(season => {
          console.log('getting season no.' + season.title);
          this.libraryService.retrieveMediaMetaData(season.key).subscribe((media) => {
            season.videos = media.video;
            season.track = media.track;
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
   * @param video - the video for download.
   */
  startDownloadingVideo(video: Video) {

    const downloadRequest: DownloadRequest = {key: video.key, mediaType: MediaType.Video};

    this.libraryService.retrieveMediaDownloadLink(downloadRequest).subscribe(downloadLink => {
      this.alertify.success('Downloading starting...');
      this.beginDownload(downloadLink);
    });

  }

  /**
   *
   * @param track - the track requesting download.
   */
  startDownloadingMusic(track: Track) {

    const downloadRequest: DownloadRequest = {key: track.key, mediaType: MediaType.Music};

    this.libraryService.retrieveMediaDownloadLink(downloadRequest).subscribe(downloadLink => {
      this.alertify.success('Downloading starting...');
      this.beginDownload(downloadLink);
    });

  }

  beginDownload(url: string) {
    const link = document.createElement('a');
    link.download = 'a';
    link.href = url;
    document.body.appendChild(link);
    link.click();
    this.onNoClick();
    // window.open(url);
  }

  resolveBannerURL(video: Video): string {
    const authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    const thumb = video.type === 'movie' ? video.art : video.grandparentArt;
    const url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    // console.log("calling: " + url);
    return url;
  }

  resolvePosterURL(video: Video) {

    const thumb = video.type === 'movie' ? video.thumb : video.grandparentThumb;

    console.log('Call 2')
    this.libraryService.retrievePhotoFromPlexServer(thumb).subscribe((photoUrl: string) => {
      this.mediaPhotoUrl = photoUrl;
    }, () => {
      console.log('Error loading photo url');
    });

  }

  resolveSeriesPosterURL(directory: Directory) {
    console.log('Call 3')

    const thumb = directory.thumb;
    this.libraryService.retrievePhotoFromPlexServer(thumb).subscribe((photoUrl: string) => {
      // return photoUrl;
      this.seriesPhotoUrl = photoUrl;
    }, () => {
      console.log('Error loading photo url');
    });
  }

}
