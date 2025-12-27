import { Component, Input } from '@angular/core';
import { NewsArticle } from '../news-cont/news-article.model';

@Component({
  selector: 'app-news-pres',
  standalone:false,
  templateUrl: './news-pres.component.html',
  styleUrl: './news-pres.component.scss'
})
export class NewsPresComponent {
  @Input() articles: NewsArticle[] = [];
  @Input() sectionTitle: string = 'Actualités & Informations';
  @Input() sectionSubtitle: string = 'Restez informé des dernières nouveautés';
  @Input() viewAllRoute: string = '/actualites';
}
