import {Deserializable} from '../deserializable.model';

export class Subscription implements Deserializable {
  active?: boolean;
  status?: string;
  plan?: string;
  features?: string[];

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
