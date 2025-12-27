import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroSlideContComponent } from './hero-slide-cont.component';

describe('HeroSlideContComponent', () => {
  let component: HeroSlideContComponent;
  let fixture: ComponentFixture<HeroSlideContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroSlideContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeroSlideContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
