import {Subscription} from './subscription.model';
import {Roles} from './roles.model';

export interface User {
  id?: number;
  uuid?: string;
  email?: string;
  joinedAt?: Date;
  username?: string;
  title?: string;
  thumb?: string;
  hasPassword?: boolean;
  authToken?: string;
  authenticationToken?: string;
  subscription?: Subscription;
  roles?: Roles;
  entitlements?: string[];
  confirmedAt?: null;
  forumId?: null;
  rememberMe?: boolean;
  jwtToken?: string;
}
