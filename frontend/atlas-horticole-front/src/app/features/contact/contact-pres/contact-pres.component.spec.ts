import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactPresComponent } from './contact-pres.component';

describe('ContactPresComponent', () => {
  let component: ContactPresComponent;
  let fixture: ComponentFixture<ContactPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
