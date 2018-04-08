import {Component, OnInit} from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {Router} from '@angular/router';
import {User} from '../../@types/user';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'pp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;

  constructor(private fb$$: FirebaseService,
              private user$$: UserService,
              private route: Router) {
  }

  ngOnInit() {
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
    this.user$$.setUser(this.user);
  }

  onLoginClick() {
    console.log(this.user);
    this.fb$$.getUser(this.user.name).subscribe((data) => {
      if (data.length !== 0) {
        const user = data[0].payload.val();
        user.userKey = data[0].payload.key;
        if (!user['beds']) {
          user['beds'] = [];
        }
        this.user$$.setUser(user);
        this.user = user;
        console.log(this.user);
        this.fb$$.signInUser(this.user.email, this.user.pass)
          .then((resp) => {
            switch (this.user.role) {
              case 'Admin':
                this.route.navigateByUrl('Admin');
                break;
              case 'Shelter Volunteer':
                this.route.navigateByUrl('ShelterVolunteer');
                break;
              case 'Homeless':
                this.route.navigateByUrl('Homeless');
                break;
            }
          })
          .catch((err) => {
            console.log(err);
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
          });
      } else {
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
    });
  }

}
