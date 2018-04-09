import {ChangeDetectorRef, Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {DirectionsRenderer} from '@ngui/map';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'pp-google-maps',
  templateUrl: './google-maps.component.html',
  styleUrls: ['./google-maps.component.css']
})
export class GoogleMapsComponent implements OnInit {
  @ViewChild(DirectionsRenderer) directionsRendererDirective: DirectionsRenderer;
  directionsRenderer: google.maps.DirectionsRenderer;
  directionsResult: google.maps.DirectionsResult;
  @Input() direction: any = {
    origin: 'Georgia Institute of Technology',
    destination: this.data['shelter']['Address'],
    travelMode: 'DRIVING'
  };


  constructor(private cdr: ChangeDetectorRef,
              public dialogRef: MatDialogRef<GoogleMapsComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.directionsRendererDirective['initialized$'].subscribe(directionsRenderer => {
      this.directionsRenderer = directionsRenderer;
    });
  }

  directionsChanged() {
    this.directionsResult = this.directionsRenderer.getDirections();
    this.cdr.detectChanges();
  }
}
