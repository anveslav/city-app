import {Component, OnInit} from '@angular/core';
import {City} from "../city";
import {CityServiceService} from "../city-service.service";
import {AuthenticationService} from "../authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {

  cities: City[];
  offset = 0;
  limit = 10;
  page: number = 1;
  totalItems: number;
  searchText: string = "";
  selected: number;
  editable: boolean;

  constructor(private cityService: CityServiceService, private authService: AuthenticationService, private router: Router) {
  }

  getDataSearchedByName() {
    this.cityService.findAllByName(this.offset, this.limit, this.searchText).subscribe(data => {
      this.cities = data.cities;
      this.totalItems = data.total;
    });
  }

  editCity(city: City) {
    this.cityService.editCity(city).subscribe((res: any) => {
     window.location.reload()
   });
  }

  onTableDataChange(event: any) {
    this.page = event;
    this.offset = this.page * this.limit - this.limit
    this.getDataSearchedByName();
  }

  ngOnInit() {
    this.getDataSearchedByName()
    this.editable = this.authService.isAllowEdit();
  }
}
