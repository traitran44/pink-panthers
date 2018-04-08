import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AngularFireModule} from 'angularfire2';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {AppComponent} from './app.component';
import {environment} from '../environments/environment';
import {AdminComponent} from './component/admin/admin.component';
import {HomelessComponent} from './component/homeless/homeless.component';
import {VolunteerComponent} from './component/volunteer/volunteer.component';
import {AngularFireDatabase} from 'angularfire2/database';
import {AngularFireAuth} from 'angularfire2/auth';
import {LoginComponent} from './component/login/login.component';
import {RegistrationComponent} from './component/registration/registration.component';
import {WelcomeComponent} from './component/welcome/welcome.component';
import {MaterialModule} from './module/material/material.module';
import {RoutingModule} from './routing.module';
import { TitleComponent } from './component/title/title.component';
import {FirebaseService} from './service/firebase.service';
import {AdminService} from './service/admin.service';
import {HomelessService} from './service/homeless.service';
import {VolunteerService} from './service/volunteer.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';
import { ShelterVolunteerComponent } from './component/shelter-volunteer/shelter-volunteer.component';
import {UserService} from './service/user.service';


@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomelessComponent,
    VolunteerComponent,
    LoginComponent,
    RegistrationComponent,
    WelcomeComponent,
    TitleComponent,
    ShelterVolunteerComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RoutingModule,
    FormsModule,
    MaterialModule,
    NgbModule.forRoot(),
    AngularFireModule.initializeApp(environment.firebase)
  ],
  providers: [
    AngularFireDatabase,
    UserService,
    FirebaseService,
    AdminService,
    HomelessService,
    VolunteerService,
    AngularFireAuth
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
