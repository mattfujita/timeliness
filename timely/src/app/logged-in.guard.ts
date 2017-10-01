import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';

@Injectable()
export class LoggedInGuard implements CanActivate {

  constructor(private http: Http, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    const checkUrl = 'http://localhost:5000/api/clients';
    const options = { withCredentials: true };

    return this.http
      .get(checkUrl, options)
      .map(() => true)
      .catch((error, caught) => {
        this.router.navigate(['/login']);
        return Observable.of(false);
      });

  }
}
