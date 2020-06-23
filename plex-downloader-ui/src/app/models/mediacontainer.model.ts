import {Video} from './video.model';
import {Device} from './device.model';
import {Directory} from './directory.model';
import {Track} from './track.model';

export interface MediaContainer {
  allowSync?: string;
  device?: Device[];
  directory?: Directory[];
  identifier?: string;
  mediaContainerId?: number;
  mediaTagPrefix?: string;
  mediaTagVersion?: string;
  mixedParents?: string;
  size?: string;
  title?: string;
  title1?: string;
  title2?: string;
  video?: Video[];
  track?: Track[];
}

// Converts JSON strings to/from your types
export class Convert {
  public static toMediaContainer(json: string): MediaContainer {
    return JSON.parse(json);
  }

  public static mediaContainerToJson(value: MediaContainer): string {
    return JSON.stringify(value);
  }
}
