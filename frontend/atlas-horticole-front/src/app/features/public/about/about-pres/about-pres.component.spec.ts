import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutPresComponent } from './about-pres.component';

describe('AboutPresComponent', () => {
  let component: AboutPresComponent;
  let fixture: ComponentFixture<AboutPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AboutPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
