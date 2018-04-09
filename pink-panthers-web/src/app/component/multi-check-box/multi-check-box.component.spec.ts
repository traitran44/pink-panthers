import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiCheckBoxComponent } from './multi-check-box.component';

describe('MultiCheckBoxComponent', () => {
  let component: MultiCheckBoxComponent;
  let fixture: ComponentFixture<MultiCheckBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultiCheckBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultiCheckBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
