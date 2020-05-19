export interface DownloadRequest {
  key?: string;
  mediaType?: MediaType;
}

export enum MediaType {
  Video,
  Music,
  Series
}
