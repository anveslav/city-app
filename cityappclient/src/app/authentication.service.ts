import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";
import {User} from "./user";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;
  public authUrl = 'http://localhost:8080/city/v1/login';

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  authenticate(username: string, password: string) {
    let token = this.createBasicAuthToken(username, password)
    return this.http.get(this.authUrl,
      {headers: {authorization: token}}).pipe(map((res: any) => {
      let user = new User();
      user.username = username;
      user.password = password;
      user.authdata = token;
      user.roles = res.roles;
      localStorage.setItem('user', JSON.stringify(user));
      console.log(user)
      this.userSubject.next(user);
    }));
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(null!);
    this.router.navigate(['/login']);
  }

  isUserLoggedIn() {
    let user = localStorage.getItem('user')
    if (user === null) return false
    return true
  }

  isAllowEdit() {
    let user = JSON.parse(localStorage.getItem('user')!);
    return user.roles.includes('ROLE_ALLOW_EDIT');
  }
}
