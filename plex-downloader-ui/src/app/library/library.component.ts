import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {LibraryService} from "../_service/library.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Constants} from "../util/constants";
import {LibraryFilterPipe} from "./library-filter.pipe";
import {LibrarySeriesFilterPipe} from "./library-series-filter.pipe";
import {Genre} from "../models/genre.model";
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit, OnDestroy {

  @Input() libraryKey: string;

  readonly MAX_ROWS = 4;

  videosList: Video[];
  // videosList = [];
  showsList: Directory[];//Series directory
  searchQuery: string;
  genreSelection: string;
  genres = new Set<string>();

  constructor(private libraryService: LibraryService,
              private route: ActivatedRoute,
              private router: Router) {


    this.router.routeReuseStrategy.shouldReuseRoute = function(){
      return false;
    };

    //Handle library change from navbar.
    const navigation = this.router.getCurrentNavigation();

    if (navigation != null) {
      let libraryKey = navigation.extras.state ? navigation.extras.state.libraryKey : null;
      if (libraryKey == null) {
        console.log('Library key is null, error!');
        return;
      }

      this.libraryKey = libraryKey;
      this.retrieveLibrary();

    }

  }

  ngOnInit() {
    if (!this.videosList && !this.showsList) {
      this.retrieveLibrary();
    }
  }

  retrieveLibrary() {
    if (!this.libraryKey) {
      console.log('Error: Didnt get the library key properly.');
      this.libraryKey = sessionStorage.getItem(Constants.PLEX_SELECTED_LIBRARY_KEY);
      console.log('The library Key: ' + this.libraryKey);
    } else {
      sessionStorage.setItem(Constants.PLEX_SELECTED_LIBRARY_KEY, this.libraryKey);
    }

    this.libraryService.retrieveLibrarySectionBySectionKeyAndDirectoryKey(this.libraryKey, 'all').subscribe(mediaContainer => {
      if (mediaContainer.video != null && mediaContainer.video.length != 0) {
        // this.videos = mediaContainer.video;
        let videos = mediaContainer.video;
        this.videosList = mediaContainer.video;

        this.genres.clear();
        for (let video of videos) {
          if (video.genres != null) {
            for (let genre of video.genres) {
              this.genres.add(genre.tag);
            }
          }
        }

      } else if (mediaContainer.directory != null && mediaContainer.directory.length != 0) {
        // this.shows = mediaContainer.directory;
        let shows = mediaContainer.directory;
        this.showsList = mediaContainer.directory;

        this.genres.clear();
        for (let show of shows) {
          if (show.genres != null) {
            for (let genre of show.genres) {
              console.log('Genre: ' + genre);
              this.genres.add(genre.tag);
            }
          }
        }
      }
    }, error => {

      console.log('Error with getting all libraries: ' + error);
    });
  }

  genreFilter(genreQuery: string){
    console.log('testing 123');
    this.genreSelection = genreQuery;
  }

  clearFilters() {
    this.searchQuery = '';
    this.genreSelection = '';
  }

  ngOnDestroy(): void {
    this.videosList = null;
    this.showsList = null;
  }

}
