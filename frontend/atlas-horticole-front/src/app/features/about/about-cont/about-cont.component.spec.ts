import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutContComponent } from './about-cont.component';

describe('AboutContComponent', () => {
  let component: AboutContComponent;
  let fixture: ComponentFixture<AboutContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AboutContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
