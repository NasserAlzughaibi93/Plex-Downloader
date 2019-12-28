import {Stream} from "./stream.model";

export interface Part {
  audioProfile?: string;
  container?:    string;
  duration?:     number;
  file?:         string;
  id?:           number;
  key?:          string;
  size?:         number;
  stream?:       Stream[];
  videoProfile?: string;
}
