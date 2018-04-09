import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'pp-multi-check-box',
  templateUrl: './multi-check-box.component.html',
  styleUrls: ['./multi-check-box.component.css']
})
export class MultiCheckBoxComponent implements OnInit {
  @Input() selectList: string[];
  @Input() selected: string[];
  @Output() listChange = new EventEmitter<any>();
  map: any;

  constructor() {
  }

  ngOnInit() {
    this.map = {};
    this.selectList.forEach((val, indx) => {
      this.map[val] = false;
    });
    if (this.selected !== null) {
      this.selected.forEach((val, indx) => {
        this.map[val] = true;
      });
    }
  }

  onChangeSelect(select) {
    if (!this.selected.includes(select)) {
      this.selected.push(select);
      this.map[select] = true;
    } else {
      this.selected.splice(this.selected.indexOf(select), 1);
      this.map[select] = false;
    }
    console.log(this.selected);
    this.listChange.emit(this.selected);
  }
}
