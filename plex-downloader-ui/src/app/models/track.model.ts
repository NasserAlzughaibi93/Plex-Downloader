import {Writer} from './writer.model';
import {Media} from './media.model';
import {Role} from './role.model';
import {Director} from './director.model';
import {Country} from './country.model';
import {Genre} from './genre.model';

export interface Track {
  grandparentTitle?: string;
  parentIndex?: string;
  grandparentThumb?: string;
  addedAt?: string;
  thumb?: string;
  parentRatingKey?: string;
  grandparentKey?: string;
  ratingKey?: string;
  type?: string;
  title?: string;
  duration?: string;
  grandparentRatingKey?: string;
  key?: string;
  grandparentGuid?: string;
  updatedAt?: string;
  summary?: string;
  art?: string;
  Media?: Media;
  index?: string;
  parentTitle?: string;
  parentThumb?: string;
  grandparentArt?: string;
  parentKey?: string;
  originalTitle?: string;
  parentGuid?: string;
  guid?: string;
}

