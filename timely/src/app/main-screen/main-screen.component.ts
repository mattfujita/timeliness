import { Component, OnInit } from '@angular/core';

// Import the Http service for AJAX calls
import { Http } from '@angular/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-screen',
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.css']
})
export class MainScreenComponent implements OnInit {

  // Add a constructor member variable declaration
  constructor(private http: Http, private router: Router ) { }

  ngOnInit() {
  }

  logout() {
    const options = {
      withCredentials: true
    };
    const logoutUrl = 'http://localhost:5000/api/session/mine';
    this.http
      .delete(logoutUrl, options)
      .subscribe(

        // On success, log a message for now?
        () => this.router.navigate(['/']),

        // On error, log a message for now?
        error => console.error('Error logging out', error)
      );
  }

}
