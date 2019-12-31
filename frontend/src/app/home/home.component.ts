import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertifyService} from "../_service/alertify.service";
import {LibraryService} from "../_service/library.service";
import {Device} from "../models/device.model";
import {Video} from "../models/video.model";
import {Constants} from "../util/constants";
import {ComponentMessagingService} from "../_service/component-messaging.service";
import {ComponentAction} from "../models/component-name.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  devices: Device[];

  videos: Video[];

  subscription: any;

  constructor(private router: Router,
              private alertify: AlertifyService,
              private libraryService: LibraryService,
              private componentMessagingService: ComponentMessagingService) {
    this.subscription = this.componentMessagingService.getMessage()
      .subscribe(message => {
        console.log('Printing message from message service: ');
        console.log(message);

        if (message.componentAction === ComponentAction.LOAD_LIBRARIES) {
          this.retrieveOnDeckLibrary();
        }
      });
  }

  ngOnInit() {
  }

  retrieveOnDeckLibrary() {
    this.devices = JSON.parse(localStorage.getItem(Constants.PLEX_SELECTED_SERVERS));
    let selected = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_NAME);
    let serverIp: string;


    this.devices.forEach(device => {

      if (selected === device.name) {
        if (device.connection.length > 0) {
          device.connection.forEach(connection => {
            if (connection.local === '0') {
              serverIp = connection.address + ':' + connection.port
            }
          });

          this.libraryService.retrieveLibraryOnDeck(serverIp).subscribe((videos) => {
            console.log('got on deck videos');
            this.videos = videos;
          }, () => {
            console.log('Error');
          });
        } else {
          console.log('Error: no connections found')
          //TODO add error call
        }
        return;
      }
    });

  }

  resolvePosterURL(video: Video): string {
    if (video.parentThumb == null) {
      return;
    }
    let authTokenHeader = '?X-Plex-Token=' + localStorage.getItem(Constants.PLEX_AUTH_TOKEN);
    let url = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI) + video.parentThumb + authTokenHeader;
    //console.log("calling: " + url);
    return url;
  }

  startDownloadingMedia(video: Video) {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    if (serverIp === null || serverIp.trim().length === 0) {
      //TODO throw error
    }

    serverIp = serverIp.replace('http://', '');

    this.libraryService.retrieveMediaDownloadLink(video, serverIp).subscribe(downloadLink => {
      this.beginDownload(downloadLink);
    });

  }

  beginDownload(url: string) {
    window.open(url);
  }

}
