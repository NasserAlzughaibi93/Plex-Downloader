import {Component, Input, OnInit} from '@angular/core';
import {Video} from "../../models/video.model";
import {Constants} from "../../util/constants";
import {Router} from "@angular/router";
import {AlertifyService} from "../../_service/alertify.service";
import {LibraryService} from "../../_service/library.service";
import {ComponentMessagingService} from "../../_service/component-messaging.service";
import {Directory} from "../../models/directory.model";

@Component({
  selector: 'app-media-card',
  templateUrl: './media-card.component.html',
  styleUrls: ['./media-card.component.css']
})
export class MediaCardComponent implements OnInit {

  @Input() video: Video;
  @Input() show: Directory;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) {}

  ngOnInit() {}

  startDownloadingMedia(video: Video) {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    if (serverIp === null || serverIp.trim().length === 0) {
      //TODO throw error
    }

    this.libraryService.retrieveMediaDownloadLink(video, serverIp).subscribe(downloadLink => {
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

  resolvePosterURL(video: Video): string {
    //TODO have server side process URL example: http://{SERVER_IP}:{PORT}/photo/:/transcode?url=/library/metadata/13686/thumb/1576691662&width=500&height=500&X-Plex-Token={{APIKEY}}
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let thumb = video.type === 'movie' ? video.thumb : video.grandparentThumb;
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }

  resolveSeriesPosterURL(directory: Directory): string {
    //TODO have server side process URL example: http://{SERVER_IP}:{PORT}/photo/:/transcode?url=/library/metadata/13686/thumb/1576691662&width=500&height=500&X-Plex-Token={{APIKEY}}
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let thumb = directory.thumb;
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_FULL_URI) + thumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }
}
