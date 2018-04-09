import {FirebaseService} from '../../service/firebase.service';
import {SnapshotAction} from 'angularfire2/database';
import {statsWarningsToString} from '@angular/cli/utilities/stats';
import {UserService} from '../../service/user.service';
import {User} from '../../@types/user';
import {MatDialog, MatOptionSelectionChange, MatSnackBar} from '@angular/material';
import {GoogleMapsComponent} from '../google-maps/google-maps.component';
import {Component, OnInit} from '@angular/core';

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
  selector: 'pp-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  shelterResp: ShelterResponse;
  shelterBuffer: Shelter[];
  shelters: Shelter[];
  searchCategories: string[];
  searchList: string[];
  genderList: string[];
  ageRangeList: string[];
  toggleSearchField: {
    select: boolean,
    type: boolean
  };
  restrictionList: string[];
  searchType: string;
  user: User;
  shelterMap: any;

  constructor(private fb$$: FirebaseService,
              private snackBar: MatSnackBar,
              public dialog: MatDialog,
              private user$$: UserService) {
  }

  ngOnInit() {
    this.user = this.user$$.getUser();
    this.toggleSearchField = {
      select: false,
      type: false
    };
    this.shelterMap = {};
    this.restrictionList = [
      'Men',
      'Women',
      'Non-binary',
      'Families w/ Newborns',
      'Families w/ Children under 5',
      'Veterans',
      'Children',
      'Young Adults'
    ];
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
        this.shelterMap[val['Shelter Name']] = val;
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
    console.log(this.shelterMap);
    const userFamilyRestriction = this.user$$.getUserFamilyType();
    const restrictions = this.shelterMap[bed]['Restrictions'].split(',');
    let isClaimable = true;

    restrictions.forEach((val, indx) => {
      restrictions[indx] = val.toLowerCase();
    });

    userFamilyRestriction.forEach((famType, indx2) => {
      if (!restrictions.includes(famType.toLowerCase())) {
        isClaimable = false;
      }
    });

    if (userFamilyRestriction.length === 0) {
      isClaimable = false;
    }

    console.log('userFamilyRestriction');
    console.log(userFamilyRestriction);
    console.log(restrictions);

    if (!this.user$$.getUserBed().includes(bed)) {
      if (isClaimable) {
        this.user$$.getUserBed().push(bed);
        this.fb$$.updateUserBeds(this.user$$.getUserKey(), this.user$$.getUserBed());
      } else {
        this.shelterMap[bed]['selected'] = false;
        this.snackBar.open('Cannot claim this bed due to restrictions', '', {
          duration: 3000,
        });
      }
    } else {
      this.shelterMap[bed]['selected'] = false;
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
        this.toggleSearchField.select = true;
        this.toggleSearchField.type = false;
        this.searchList = this.genderList;
        break;
      case 'Age Range':
        this.searchType = 'age';
        this.toggleSearchField.select = true;
        this.toggleSearchField.type = false;
        this.searchList = this.ageRangeList;
        break;
      case 'Name':
        this.searchType = 'name';
        this.toggleSearchField.select = false;
        this.toggleSearchField.type = true;
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

  onFamilyTypeChange($event: any) {
    console.log($event);
    this.fb$$.updateFamilyType(this.user$$.getUserKey(), $event);
  }

  onFamilySizeChange($event) {
    this.fb$$.updateFamilySize(this.user$$.getUserKey(), $event.value);
  }

  getDirection(eachShelter: Shelter) {
    console.log(eachShelter);
    this.openDialog(eachShelter);
  }

  openDialog(shelter: Shelter): void {
    const dialogRef = this.dialog.open(GoogleMapsComponent, {
      panelClass: 'g-map',
      data: {shelter: shelter}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
