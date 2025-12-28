import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualitesContComponent } from './actualites-cont.component';

describe('ActualitesContComponent', () => {
  let component: ActualitesContComponent;
  let fixture: ComponentFixture<ActualitesContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualitesContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualitesContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
