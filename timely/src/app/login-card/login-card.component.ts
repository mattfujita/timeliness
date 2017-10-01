import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-card',
  templateUrl: './login-card.component.html',
  styleUrls: ['./login-card.component.css']
})
export class LoginCardComponent implements OnInit {

  private username = '';
  private password = '';
  private error: string;

  constructor(private http: Http, private router: Router) { }

  ngOnInit() {
  }

  get disableButton() {
    return this.username.length === 0 ||
           this.password.length === 0;
  }

  submitCredentials() {
    const payload = {
      username: this.username,
      password: this.password
    }

    const options = {
      withCredentials: true
    }

    const cookieUrl = 'http://localhost:5000/api/clients';
    const sessionUrl = 'http://localhost:5000/api/session/mine';

    this.http
        .get(cookieUrl, options)
        .catch(() => this.http.put(sessionUrl, payload, options))
        .subscribe(
          () => {
            this.error = '';
            this.router.navigate(['/main']);
          },

          e => {
            if(e.status === 401) {
              this.error = 'Could not log in with those credentials';
            }
          },

        );

  }

}
