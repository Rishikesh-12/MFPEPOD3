import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountViewComponent } from './account-view/account-view.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { CustomerHomeComponent } from './customer-home/customer-home.component';
import { DepositWithdrawComponent } from './deposit-withdraw/deposit-withdraw.component';
import { EmployeeHomeComponent } from './employee-home/employee-home.component';
import { HomeComponent } from './home/home.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { LoginComponent } from './login/login.component';
import { MiniStatementComponent } from './mini-statement/mini-statement.component';
import { RegistrationComponent } from './registration/registration.component';
import { UpdateComponent } from './update/update.component';
import { CustomerComponent } from './customer/customer.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { TransferMoneyComponent } from './transfer-money/transfer-money.component';
import { TransactionsComponent } from './transactions/transactions.component';

const routes: Routes = [
  {
    path:'register',
    component:RegistrationComponent
  },
  {
    path:'welcome',
    component:CustomerHomeComponent
  },
  {
    path:'update',
    component:UpdateComponent
  },
  {
    path:'update/:id',
    component:UpdateComponent
  },
  {
    path:'landing',
    component:LandingPageComponent
  },
  {
    path:'login',
    component:LoginComponent
  },
  {
    path:'admin',
    component:AdminLoginComponent
  },
  {
    path:'employee',
    component:EmployeeHomeComponent
  },
  {
    path:'home',
    component:HomeComponent
  },
  {
    path:'view',
    component:AccountViewComponent
  },
  {
    path:'deposit-withdraw',
    component:DepositWithdrawComponent
  },
  {
    path:'statement',
    component:MiniStatementComponent
  },
  {path:'customer',component:CustomerComponent},
  {path:'withdraw',component:WithdrawComponent},
  {path:'transfer-money',component:TransferMoneyComponent},
  {path:'transactions',component:TransactionsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
