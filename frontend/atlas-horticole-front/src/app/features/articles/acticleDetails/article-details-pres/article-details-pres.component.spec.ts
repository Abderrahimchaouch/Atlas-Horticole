import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleDetailsPresComponent } from './article-details-pres.component';

describe('ArticleDetailsPresComponent', () => {
  let component: ArticleDetailsPresComponent;
  let fixture: ComponentFixture<ArticleDetailsPresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleDetailsPresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleDetailsPresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
