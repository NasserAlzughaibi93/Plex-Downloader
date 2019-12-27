import {Deserializable} from '../deserializable.model';

export class Roles implements Deserializable {
  roles?: string[];

  deserialize(input: any): this {
    return Object.assign(this, input);
  }
}
