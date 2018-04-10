import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';
import {UserService} from './service/user.service';
import {User} from './@types/user';

@Component({
  selector: 'pp-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  user: User;

  constructor(private db: AngularFireDatabase,
              private user$$: UserService,
              private auth: AngularFireAuth) {
    this.auth.auth.signInWithEmailAndPassword('traitran44@gmail.com', 'pinkpanther').then((resp) => {
      console.log(resp);
    });
  }

  ngOnInit() {
    this.user = {
      familyType: [],
      familySize: 1,
      userKey: '',
      name: '',
      email: '',
      pass: '',
      role: '',
      beds: []
    };
    this.user$$.setUser(this.user);
  }

  pushUser() {
    this.db.list('user').push({
      name: 'Trai',
      password: '123',
      type: 'admin'
    });
  }
}
