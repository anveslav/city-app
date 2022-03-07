import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CityResponse} from "./city-response";
import {City} from "./city";
import {CanActivate, Router} from "@angular/router";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class AuthGuard  implements CanActivate {
  constructor(public auth: AuthenticationService, public router: Router) {
  }

  canActivate(): boolean {
    if (!this.auth.isUserLoggedIn()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
