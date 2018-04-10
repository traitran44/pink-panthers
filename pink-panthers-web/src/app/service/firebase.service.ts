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

  updateFamilyType(userKey: any, type: string[]) {
    return this.db.list('accounts').update(userKey, {
      familyType: type
    });
  }

  updateFamilySize(userKey: any, size: number) {
    return this.db.list('accounts').update(userKey, {
      familySize: size
    });
  }

  updateUserName(userKey: any, name: string) {
    return this.db.list('accounts').update(userKey, {
      name: name
    });
  }

  updateUserEmail(userKey: any, email: string) {
    return this.db.list('accounts').update(userKey, {
      email: email
    });
  }

  updateUserBeds(userKey: any, bed: any) {
    return this.db.list('accounts').update(userKey, {
      beds: bed
    });
  }

  getShelters() {
    return this.db.list('data').snapshotChanges();
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
