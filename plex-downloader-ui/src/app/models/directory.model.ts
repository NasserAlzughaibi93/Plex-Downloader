import {Location} from "./location.model";
import {Genre} from "./genre.model";
import {Role} from "./role.model";
import {Video} from "./video.model";


export interface Directory {
  agent?:            string;
  allowSync?:        string;
  art?:              string;
  composite?:        string;
  content?:          string;
  contentChangedAt?: string;
  createdAt?:        string;
  directory?:        string;
  directoryId?:      number;
  filters?:          string;
  key?:              string;
  secondary?:        string;
  prompt?:           string;
  search?:           string;
  language?:         string;
  location?:         Location;
  refreshing?:       string;
  scannedAt?:        string;
  scanner?:          string;
  thumb?:            string;
  title?:            string;
  type?:             string;
  updatedAt?:        string;
  uuid?:             string;
  summary?:          string;
  banner?:           string;
  genres?:           Genre[];
  roles?:            Role[];
  videos?:           Video[];
  childCount?:       string;
  year?:             number;
  rating?:           number;
  transcodedPhoto?:  string;
  leafCount?:        number;
  viewedLeafCount?:  number;
}
