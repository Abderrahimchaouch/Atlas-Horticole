import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleDetailsContComponent } from './article-details-cont.component';

describe('ArticleDetailsContComponent', () => {
  let component: ArticleDetailsContComponent;
  let fixture: ComponentFixture<ArticleDetailsContComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleDetailsContComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleDetailsContComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
