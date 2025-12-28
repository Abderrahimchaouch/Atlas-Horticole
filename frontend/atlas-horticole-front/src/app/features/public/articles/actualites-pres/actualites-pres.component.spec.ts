import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActualitesPresComponent } from './actualites-pres.component';

describe('ActualitesPresComponent', () => {
  let component: ActualitesPresComponent;
  let fixture: ComponentFixture<ActualitesPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActualitesPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActualitesPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
