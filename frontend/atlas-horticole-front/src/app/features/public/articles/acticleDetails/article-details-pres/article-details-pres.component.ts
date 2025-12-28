import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NewsArticle } from '../../../../../core/models/articles.model';


@Component({
  selector: 'app-article-detail-pres',
  standalone: false,
  templateUrl: './article-details-pres.component.html',
  styleUrl: './article-details-pres.component.scss'
})
export class ArticleDetailsPresComponent {
  @Input() article?: NewsArticle;
  @Input() relatedArticles: NewsArticle[] = [];

  @Output() share = new EventEmitter<string>();
  @Output() print = new EventEmitter<void>();

  onShare(platform: string): void {
    this.share.emit(platform);
  }

  onPrint(): void {
    this.print.emit();
  }

  getFormattedContent(): string {
    if (!this.article?.content) return '';

    // Convertir le markdown simple en HTML
    return this.article.content
      .replace(/## (.*?)\n/g, '<h2>$1</h2>')
      .replace(/### (.*?)\n/g, '<h3>$1</h3>')
      .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
      .replace(/\n- (.*?)(?=\n|$)/g, '<li>$1</li>')
      .replace(/\n/g, '<br>');
  }
}