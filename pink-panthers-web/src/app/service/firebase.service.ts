import {Injectable} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';

@Injectable()
export class FirebaseService {

  constructor(private db: AngularFireDatabase,
              private auth: AngularFireAuth) {
  }

  createNewUser(email: string, pass: string): any {
    return this.auth.auth
      .createUserWithEmailAndPassword(email, pass);
  }

  signInUser(email: string, pass: string): any {
    return this.auth.auth
      .signInWithEmailAndPassword(email, pass);
  }

  getUser(name: string): any {
    return this.db.list('accounts', ref => {
      console.log(ref);
      return ref.orderByChild('name').equalTo(name);
    }).snapshotChanges();
  }

  pushData(table, data): any {
    return this.db.list(table).push(data);
  }

}
