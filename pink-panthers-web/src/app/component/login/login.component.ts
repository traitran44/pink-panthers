import { Component, OnInit } from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {Router} from '@angular/router';

@Component({
  selector: 'pp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: {
    name: string,
    email: string,
    pass: string,
    role: string
  };

  constructor(private fb$$: FirebaseService,
              private route: Router) { }

  ngOnInit() {
    this.user = {
      name: '',
      email: '',
      pass: '',
      role: ''
    };
  }

  onLoginClick() {
    console.log(this.user);
   this.fb$$.getUser(this.user.name).subscribe((data) => {
     if (data.length !== 0) {
       const user = data[0].payload.val();
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
             name: '',
             email: '',
             pass: '',
             role: ''
           };
         });
     } else {
       this.user = {
         name: '',
         email: '',
         pass: '',
         role: ''
       };
     }
   });
  }

}
