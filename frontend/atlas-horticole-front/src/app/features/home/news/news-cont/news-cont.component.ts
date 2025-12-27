import { Component, OnInit } from '@angular/core';
import { NewsArticle } from './news-article.model';

@Component({
  selector: 'app-news-cont',
  standalone: false,
  templateUrl: './news-cont.component.html',
  styleUrl: './news-cont.component.scss'
})
export class NewsContComponent implements OnInit {
  sectionTitle = 'Actualités & Informations';
  sectionSubtitle = 'Restez informé des dernières nouveautés';
  viewAllRoute = '/actualites';

  articles: NewsArticle[] = [
    {
      id: '1',
      title: 'Nouvelle gamme de biostimulants organiques',
      excerpt: 'Découvrez notre dernière innovation en matière d\'amendement de sol. ORGANIC SOL, une formulation 100% végétale pour améliorer la fertilité de vos cultures.',
      date: new Date('2024-12-15'),
      route: '/actualites/nouvelle-gamme-biostimulants',
      category: 'Innovation',
      imageUrl: 'images/arcticle1.png'
    },
    {
      id: '2',
      title: 'Formation technique : Lutte intégrée',
      excerpt: 'Participez à nos sessions de formation sur le biocontrôle et la pollinisation. Nos experts vous accompagnent pour optimiser vos pratiques agricoles.',
      date: new Date('2024-12-10'),
      route: '/actualites/formation-lutte-integree',
      category: 'Formation',
      imageUrl: 'images/article2.png'
    },
    {
      id: '3',
      title: 'Atlas Horticole obtient une nouvelle certification',
      excerpt: 'Notre engagement pour la qualité reconnu au niveau international. Une nouvelle étape dans notre démarche d\'excellence et de développement durable.',
      date: new Date('2024-12-05'),
      route: '/actualites/nouvelle-certification',
      category: 'Entreprise',
      imageUrl: 'images/article3.png'
    }
  ];

  ngOnInit(): void {
    // Possibilité de charger depuis un service
    // this.loadArticles();
  }

  // private loadArticles(): void {
  //   this.newsService.getLatestArticles(3).subscribe(
  //     articles => this.articles = articles
  //   );
  // }

}
