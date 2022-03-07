import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {NgxPaginationModule} from 'ngx-pagination';


import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CityListComponent} from './city-list/city-list.component';
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatTableModule} from "@angular/material/table";
import {LoginComponent} from './login/login.component';
import {HttpInterceptorService} from "./http-interceptor.service";
import {MenuComponent} from './menu/menu.component';
import {AuthGuard} from "./auth-guard";
import {AuthenticationService} from "./authentication.service";


@NgModule({
  declarations: [
    AppComponent,
    CityListComponent,
    LoginComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxPaginationModule,
    MatInputModule,
    FormsModule,
    MatFormFieldModule,
    MatIconModule,
    BrowserAnimationsModule,
    MatTableModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
      deps: [AuthenticationService]
    },
    AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
