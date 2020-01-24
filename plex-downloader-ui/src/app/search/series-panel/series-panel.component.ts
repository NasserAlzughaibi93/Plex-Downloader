import {Component, Input, OnInit} from '@angular/core';
import {Directory} from "../../models/directory.model";
import {LibraryService} from "../../_service/library.service";
import {Video} from "../../models/video.model";
import {Constants} from "../../util/constants";

@Component({
  selector: 'app-series-panel',
  templateUrl: './series-panel.component.html',
  styleUrls: ['./series-panel.component.css']
})
export class SeriesPanelComponent implements OnInit {

  @Input() show: Directory;

  seasons = new Array<Directory>();

  constructor(private libraryService: LibraryService) {}

  ngOnInit() {
    if (this.show) {
      console.log("calling for more");
      this.libraryService.retrieveMediaMetaDataChildren(this.show.key).subscribe((directories) => {

        this.seasons = directories;

        let indexToRemove  = this.seasons.findIndex(season => season.title === 'All episodes');

        this.seasons.splice(indexToRemove, 1);

        this.seasons.forEach(season => {
          this.libraryService.retrieveMediaMetaData(season.key).subscribe((videos) => {
            season.videos = videos;
          });
        });

      });
    }
  }

  startDownloadingMedia(video: Video) {
    let serverIp = localStorage.getItem(Constants.PLEX_SELECTED_SERVER_URI);
    if (serverIp === null || serverIp.trim().length === 0) {
      //TODO throw error
    }

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
