import {Component, OnInit} from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {Router} from '@angular/router';

@Component({
  selector: 'pp-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: {
    name: string,
    pass: string,
    email: string
  };


  constructor(private fb$$: FirebaseService,
              private route: Router) {
    this.user = {
      name: '',
      pass: '',
      email: ''
    };
  }

  ngOnInit() {
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
