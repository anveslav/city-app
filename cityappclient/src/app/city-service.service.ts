import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CityResponse} from "./city-response";
import {City} from "./city";

@Injectable({
  providedIn: 'root'
})
export class CityServiceService {

  private cityUrl: string;
  private cityUrlV2: string;

  constructor(private http: HttpClient) {
    this.cityUrl = 'http://localhost:8080/city/v1/cities';
    this.cityUrlV2 = 'http://localhost:8080/city/v1/citiesV2';
  }

  public findAllByName(offset: number, limit: number, text: string): Observable<CityResponse> {
    return this.http.get<CityResponse>(this.cityUrl + `?offset=${offset}&limit=${limit}&searchText=${text}`);
  }

   public findAllByNameV2(page: number, limit: number, text: string): Observable<CityResponse> {
      return this.http.get<CityResponse>(this.cityUrlV2 + `?page=${page}&limit=${limit}&searchText=${text}`);
    }

  public editCity(city: City): Observable<any> {
    return this.http.patch(this.cityUrl + `/${city.id}`, city);
  }
}
