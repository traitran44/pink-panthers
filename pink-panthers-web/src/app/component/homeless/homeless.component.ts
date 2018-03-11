import {Component, OnInit} from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {SnapshotAction} from 'angularfire2/database';

interface ShelterResponse {
  'Address': string[];
  'Capacity': string[];
  'Latitude': string[];
  'Longitude': string[];
  'Phone Number': string[];
  'Restrictions': string[];
  'Shelter Name': string[];
  'Special Notes': string[];
}

interface Shelter {
  'Address': string;
  'Capacity': string;
  'Latitude': string;
  'Longitude': string;
  'Phone Number': string;
  'Restrictions': string;
  'Shelter Name': string;
  'Special Notes': string;
}

@Component({
  selector: 'pp-homeless',
  templateUrl: './homeless.component.html',
  styleUrls: ['./homeless.component.css']
})
export class HomelessComponent implements OnInit {
  shelterResp: ShelterResponse;
  shelters: Shelter[];
  searchCategories: string[];
  searchList: string[];
  genderList: string[];
  ageRangeList: string[];
  toggleSeachField: {
    select: boolean,
    type: boolean
  };

  constructor(private fb$$: FirebaseService) {
  }

  ngOnInit() {
    this.toggleSeachField = {
      select: false,
      type: false
    };
    this.genderList = [
      'None',
      'Men',
      'Women',
      'Non-binary'
    ];
    this.ageRangeList = [
      'None',
      'Families with Newborns',
      'Children',
      'Young Adults',
      'Anyone'
    ];
    this.searchList = [];
    this.searchCategories = [
      'Gender',
      'Age Range',
      'Name'
    ];
    this.shelterResp = {
      'Address': [],
      'Capacity': [],
      'Latitude': [],
      'Longitude': [],
      'Phone Number': [],
      'Restrictions': [],
      'Shelter Name': [],
      'Special Notes': []
    };
    this.shelters = [];
    this.fb$$.getShelters().subscribe((snapShot: SnapshotAction[]) => {
      snapShot.forEach((data, indx) => {
        this.shelterResp[data.key] = data.payload.val();
      });

      // get each indx
      this.shelterResp['Address'].forEach((val, indx) => {
        let shelter: Shelter = {
          'Address': '',
          'Capacity': '',
          'Latitude': '',
          'Longitude': '',
          'Phone Number': '',
          'Restrictions': '',
          'Shelter Name': '',
          'Special Notes': ''
        };
        // get each key
        for (const key in this.shelterResp) {
          // get data at each indx
          shelter[key] = this.shelterResp[key][indx];
        }
        this.shelters.push(shelter);
      });

      console.log('this.shelters');
      console.log(this.shelters);
    });
  }

  onSearchCategories($event) {
    console.log($event);
    switch($event.value) {
      case 'Gender':
        this.toggleSeachField.select = true;
        this.toggleSeachField.type = false;
        this.searchList = this.genderList;
        break;
      case 'Age Range':
        this.toggleSeachField.select = true;
        this.toggleSeachField.type = false;
        this.searchList = this.ageRangeList;
        break;
      case 'Name':
        this.toggleSeachField.select = false;
        this.toggleSeachField.type = true;
        break;
      default:
        break;
    }
  }

  onTypeSearch($event) {
    console.log($event.target.value);
  }
}
