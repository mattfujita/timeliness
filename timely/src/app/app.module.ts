import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { RegistrationCtaComponent } from './registration-cta/registration-cta.component';
import { LoginCardComponent } from './login-card/login-card.component';
import { SignUpCardComponent } from './sign-up-card/sign-up-card.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainScreenComponent } from './main-screen/main-screen.component';
import { MyListingsComponent } from './my-listings/my-listings.component';
import { LoggedInGuard } from './logged-in.guard';

const appRoutes: Routes = [
  { path: 'login', component: LoginPageComponent },

  { path: 'signup', component: SignUpCardComponent },

  { path: 'main', component: MainScreenComponent, canActivate: [LoggedInGuard] },
  
    // Redirect to login for empty path
  { path: '',  redirectTo: '/login', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationCtaComponent,
    LoginCardComponent,
    SignUpCardComponent,
    LoginPageComponent,
    MainScreenComponent,
    MyListingsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [LoggedInGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
