import {Component, OnInit} from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {Router} from '@angular/router';
import {User} from '../../@types/user';

@Component({
  selector: 'pp-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User;
  roleList: string[];


  constructor(private fb$$: FirebaseService,
              private route: Router) {
  }

  ngOnInit() {
    this.roleList = [
      'Homeless',
      'Shelter Volunteer',
      'Admin'
    ];
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

  onRegisterClick() {
    console.log(this.user);
    this.fb$$.createNewUser(this.user.email, this.user.pass)
      .then((resp) => {
        this.fb$$.pushData('accounts', this.user).then(() => {
          this.route.navigateByUrl('Login');
        });
      })
      .catch((err) => {
        console.log('Err');
      });
  }
}
