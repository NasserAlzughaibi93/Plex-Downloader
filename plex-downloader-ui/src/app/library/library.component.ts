import {Component, OnDestroy, OnInit} from '@angular/core';
import {LibraryService} from "../_service/library.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Constants} from "../util/constants";

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit, OnDestroy {

  readonly MAX_ROWS = 4;

  videosList = [];
  showsList = []; //Series directory

  constructor(private libraryService: LibraryService,
              private route: ActivatedRoute,
              private router: Router) {


    this.router.routeReuseStrategy.shouldReuseRoute = function(){
      return false;
    };

    const navigation = this.router.getCurrentNavigation();
    let libraryKey = navigation.extras.state ? navigation.extras.state.libraryKey : null;

    if (!libraryKey) {
      console.log('Error: Didnt get the library key properly.');
      libraryKey = sessionStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);
      console.log('The library Key: ' + libraryKey);
    } else {
      sessionStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_KEY, libraryKey);
    }

    this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(libraryKey, 'all').subscribe(mediaContainer => {
      if (mediaContainer.video != null && mediaContainer.video.length != 0) {
        // this.videos = mediaContainer.video;
        let videos = mediaContainer.video;

        for (let i = 0; i < videos.length; i += this.MAX_ROWS) {

          this.videosList.push({ videos: videos.slice(i, i + this.MAX_ROWS) });
        }

      } else if (mediaContainer.directory != null && mediaContainer.directory.length != 0) {
        // this.shows = mediaContainer.directory;
        let shows = mediaContainer.directory;

        for (let i = 0; i < shows.length; i += this.MAX_ROWS) {

          this.showsList.push({ shows: shows.slice(i, i + this.MAX_ROWS) });
        }
      }
    }, error => {

      console.log('Error with getting all libraries: ' + error);
    });

  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.videosList = null;
    this.showsList = null;
  }

}
