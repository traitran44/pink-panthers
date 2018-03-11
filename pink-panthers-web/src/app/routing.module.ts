import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {WelcomeComponent} from './component/welcome/welcome.component';
import {LoginComponent} from './component/login/login.component';
import {RegistrationComponent} from './component/registration/registration.component';
import {AdminComponent} from './component/admin/admin.component';
import {HomelessComponent} from './component/homeless/homeless.component';
import {ShelterVolunteerComponent} from './component/shelter-volunteer/shelter-volunteer.component';

const routes: Routes = [
  {
    path: 'Login',
    component: LoginComponent
  },
  {
    path: 'Home',
    component: AdminComponent
  },
  {
    path: 'Admin',
    component: AdminComponent
  },
  {
    path: 'Homeless',
    component: HomelessComponent
  },
  {
    path: 'ShelterVolunteer',
    component: ShelterVolunteerComponent
  },
  {
    path: 'Register',
    component: RegistrationComponent
  },
  {
    path: 'Welcome',
    component: WelcomeComponent
  },
  {
    path: '',
    redirectTo: '/Welcome',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}]
})
export class RoutingModule {
}
