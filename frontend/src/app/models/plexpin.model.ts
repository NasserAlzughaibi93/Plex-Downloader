export interface PlexPin {
  id: number;
  code: string;
  expiresIn: string;
  createdAt: string;
  product: string;
  trusted: string;
  clientIdentifier: string;
  authToken: string;
  location: Location;
  expiresAt: string;
  resolvedUri: string;
}
