import { Component } from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';

@Component({
  selector: 'pp-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private db: AngularFireDatabase,
              private auth: AngularFireAuth) {
    this.auth.auth.signInWithEmailAndPassword('traitran44@gmail.com', 'pinkpanther').then((resp) => {
      console.log(resp);
    });
  }

  pushUser() {
    this.db.list('user').push({
      name: 'Trai',
      password: '123',
      type: 'admin'
    });
  }
}
