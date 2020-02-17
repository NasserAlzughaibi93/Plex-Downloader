import { Pipe, PipeTransform } from '@angular/core';
import {Video} from "../models/video.model";
import {Directory} from "../models/directory.model";

@Pipe({
  name: 'libraryFilter'
})
export class LibraryFilterPipe implements PipeTransform {

  transform(videosList: Video[], searchQuery: string): Video[] {

    //console.log("Search term: " + searchQuery);

    if (!videosList || !searchQuery) {
      return videosList;
    }

    return videosList.filter(video => video.title.toLowerCase().indexOf(searchQuery.toLowerCase()) !== -1);
  }
}
