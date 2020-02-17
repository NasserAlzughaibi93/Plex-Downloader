import { Pipe, PipeTransform } from '@angular/core';
import {Video} from "../models/video.model";

@Pipe({
  name: 'libraryGenreFilter'
})
export class LibraryGenreFilterPipe implements PipeTransform {

  transform(videosList: Video[], genre: string): Video[] {
    if (!videosList || !genre) {
      return videosList;
    }

    return videosList.filter(video => {
      console.log('video name: ' + video.title);
      for (let aGenre of video.genres) {
        console.log('the genre' + aGenre);
        if (aGenre.tag === genre) {
          return video;
        }
      }
    });
  }

}
