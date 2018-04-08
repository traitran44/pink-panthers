import { Injectable } from '@angular/core';
import {User} from '../@types/user';

@Injectable()
export class UserService {
  user: User;
  constructor() {
    this.user = {
      userKey: '',
      name: '',
      email: '',
      pass: '',
      role: '',
      beds: []
    };
  }

  setUser(newUser) {
    console.log(newUser);
    this.user = JSON.parse(JSON.stringify(newUser));
  }

  getUser() {
    return this.user;
  }

  getUserBed() {
    console.log('Get beds');
    console.log(this.user);
    return this.user.beds;
  }
  getUserKey() {
    return this.user.userKey;
  }
}
