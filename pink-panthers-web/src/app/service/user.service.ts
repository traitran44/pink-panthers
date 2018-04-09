import {Injectable} from '@angular/core';
import {User} from '../@types/user';

@Injectable()
export class UserService {
  user: User;

  constructor() {
    this.user = {
      userKey: '',
      name: '',
      pass: '',
      email: '',
      role: '',
      familySize: 1,
      familyType: [],
      beds: []
    };
  }

  setUser(newUser) {
    this.user = JSON.parse(JSON.stringify(newUser));
  }

  getUser(): User {
    return this.user;
  }

  getUserFamilyType(): string[] {
    return this.user.familyType;
  }

  getUserBed(): string[] {
    return this.user.beds;
  }

  getUserKey(): string {
    return this.user.userKey;
  }
}
