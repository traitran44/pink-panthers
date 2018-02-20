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
    pass: string
  };

  constructor(private fb$$: FirebaseService,
              private route: Router) { }

  ngOnInit() {
    this.user = {
      name: '',
      email: '',
      pass: ''
    };
  }

  onLoginClick() {
    console.log(this.user);
   this.fb$$.getUser(this.user.name).subscribe((data) => {
     if (data.length !== 0) {
       const user = data[0].payload.val();
       this.user.email = user.email;
       console.log(this.user);
       this.fb$$.signInUser(this.user.email, this.user.pass)
         .then((resp) => {
           console.log('Sign in');
           this.route.navigateByUrl('Admin');
         })
         .catch((err) => {
           console.log(err);
           this.user = {
             name: '',
             email: '',
             pass: ''
           };
         });
     } else {
       this.user = {
         name: '',
         email: '',
         pass: ''
       };
     }
   });
  }

}
