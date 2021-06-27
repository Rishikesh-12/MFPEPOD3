import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { RegistrationComponent } from './registration/registration.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
const routes: Routes = [
  {path:'',component:LandingPageComponent},
  {path:'login',component:LoginComponent},
  {path:'welcome-page',component:WelcomePageComponent},
  {path:'registration',component:RegistrationComponent},
  {path:'account-details',component:AccountDetailsComponent},
  {path:'landing-page',component:LandingPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
