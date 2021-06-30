import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule } from '@angular/forms';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { CustomerHomeComponent } from './customer-home/customer-home.component';
import { UpdateComponent } from './update/update.component';
import { LoginComponent } from './login/login.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SafePipe } from './safe.pipe';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { EmployeeHomeComponent } from './employee-home/employee-home.component';
import { HomeComponent } from './home/home.component';
import { AccountViewComponent } from './account-view/account-view.component';
import { DepositWithdrawComponent } from './deposit-withdraw/deposit-withdraw.component';
import { MiniStatementComponent } from './mini-statement/mini-statement.component'
import { NgxPrintModule } from 'ngx-print'

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    CustomerHomeComponent,
    UpdateComponent,
    LoginComponent,
    LandingPageComponent,
    SafePipe,
    AdminLoginComponent,
    EmployeeHomeComponent,
    HomeComponent,
    AccountViewComponent,
    DepositWithdrawComponent,
    MiniStatementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BsDatepickerModule.forRoot(),
    BrowserAnimationsModule,
    NgxPrintModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
