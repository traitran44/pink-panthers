import {Component, OnInit} from '@angular/core';
import {FirebaseService} from '../../service/firebase.service';
import {SnapshotAction} from 'angularfire2/database';
import {statsWarningsToString} from '@angular/cli/utilities/stats';
import {UserService} from '../../service/user.service';
import {User} from '../../@types/user';

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
  selected: boolean;
}

@Component({
  selector: 'pp-homeless',
  templateUrl: './homeless.component.html',
  styleUrls: ['./homeless.component.css']
})
export class HomelessComponent implements OnInit {
  shelterResp: ShelterResponse;
  shelterBuffer: Shelter[];
  shelters: Shelter[];
  searchCategories: string[];
  searchList: string[];
  genderList: string[];
  ageRangeList: string[];
  toggleSeachField: {
    select: boolean,
    type: boolean
  };
  searchType: string;
  user: User;

  constructor(private fb$$: FirebaseService,
              private user$$: UserService) {
  }

  ngOnInit() {
    this.user = this.user$$.getUser();
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
      'Families w/ Newborns',
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

      console.log('this.shelterResp');
      console.log(this.shelterResp);

      // get each indx
      this.shelterResp['Address'].forEach((val, indx) => {
        const shelter: Shelter = {
          'Address': '',
          'Capacity': '',
          'Latitude': '',
          'Longitude': '',
          'Phone Number': '',
          'Restrictions': '',
          'Shelter Name': '',
          'Special Notes': '',
          'selected': false
        };


        // get each key
        for (const key in this.shelterResp) {
          // get data at each indx
          shelter[key] = this.shelterResp[key][indx];
        }
        this.shelters.push(shelter);
      });

      this.shelterBuffer = this.shelters.slice();
      this.shelterBuffer.forEach((val, indx) => {
        if (this.user$$.getUserBed().includes(val['Shelter Name'])) {
          val['selected'] = true;
        }
      });
      this.shelters = this.shelterBuffer;
      console.log('this.shelters');
      console.log(this.shelters);
    });
  }

  claimBed(bed) {
    console.log('bed');
    console.log(bed);
    console.log(this.user$$.getUserBed());

    if (!this.user$$.getUserBed().includes(bed)) {
      this.user$$.getUserBed().push(bed);
      this.fb$$.updateUserBeds(this.user$$.getUserKey(), this.user$$.getUserBed());
    } else {
      const rmvIndx = this.user$$.getUserBed().indexOf(bed);
      this.user$$.getUserBed().splice(rmvIndx, 1);
      this.fb$$.updateUserBeds(this.user$$.getUserKey(), this.user$$.getUserBed());
    }
  }

  onCategoryChange($event) {
    console.log($event);
    switch ($event.value) {
      case 'Gender':
        this.searchType = 'gender';
        this.toggleSeachField.select = true;
        this.toggleSeachField.type = false;
        this.searchList = this.genderList;
        break;
      case 'Age Range':
        this.searchType = 'age';
        this.toggleSeachField.select = true;
        this.toggleSeachField.type = false;
        this.searchList = this.ageRangeList;
        break;
      case 'Name':
        this.searchType = 'name';
        this.toggleSeachField.select = false;
        this.toggleSeachField.type = true;
        break;
      default:
        break;
    }
  }

  onGenderSearch($event) {
    console.log($event.value);
    const gender = $event.value;
    const res = [];
    this.shelterBuffer.forEach((val, indx) => {
      console.log(val['Restrictions'].split(','));
      val['Restrictions'].split(',').forEach((restriction) => {
        if (restriction === gender) {
          res.push(val);
        }
      });
    });

    if (res.length === 0) {
      this.shelters = this.shelterBuffer.slice();
    } else {
      this.shelters = res;
    }
  }

  onAgeSearch($event) {
    console.log($event);

    const res = [];
    this.shelterBuffer.forEach((val, indx) => {
      val['Restrictions'].split(',').forEach((restriction) => {
        switch ($event.value.toLowerCase()) {
          case 'none':
            this.shelters = this.shelterBuffer;
            break;
          case 'anyone':
            if (restriction.toLowerCase().includes('anyone')) {
              res.push(val);
            }
            break;
          case 'children':
            if (restriction.toLowerCase().includes('children')) {
              res.push(val);
            }
            break;
          case 'families w/ newborns':
            if (restriction.toLowerCase().includes('families')) {
              res.push(val);
            }
            break;
          case 'young adults':
            if (restriction.toLowerCase().includes('young adults')) {
              res.push(val);
            }
            break;
        }
      });
    });
    console.log('Result: ');
    console.log(res);
    if (res.length === 0) {
      this.shelters = this.shelterBuffer.slice();
    } else {
      this.shelters = res;
    }
  }


  onNameSearch($event) {
    const res = [];
    this.shelterBuffer.forEach((val, indx) => {
      if (val['Shelter Name'].toLowerCase().includes($event.target.value.toLowerCase())) {
        res.push(val);
      }
    });
    if (res.length === 0) {
      this.shelters = this.shelterBuffer.slice();
    } else {
      this.shelters = res;
    }
  }

  onSearch($event) {
    switch (this.searchType) {
      case 'age':
        this.onAgeSearch($event);
        break;
      case 'name':
        this.onNameSearch($event);
        break;
      case 'gender':
        this.onGenderSearch($event);
        break;
    }
  }
}
