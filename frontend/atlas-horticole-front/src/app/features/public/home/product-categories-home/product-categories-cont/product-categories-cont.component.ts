import { Component, OnInit } from '@angular/core';
import { ProductCategory } from '../../../../../core/models/product-categories.model';

@Component({
  selector: 'app-product-categories-cont',
  standalone: false,
  templateUrl: './product-categories-cont.component.html',
  styleUrl: './product-categories-cont.component.scss'
})
export class ProductCategoriesContComponent implements OnInit {
  sectionTitle = 'Nos Expertises';
  sectionSubtitle = 'Des solutions adaptées à tous vos besoins agricoles';

  categories: ProductCategory[] = [
    {
      id: 'Biostimulants',
      title: 'Biostimulants',
      description: 'Amendement organique pour améliorer la fertilité du sol et stimuler la croissance',
      route: '/produits/biostimulants',
      icon: 'plant',
      imageUrl: 'images/Biostimulant.jpeg',
    },
    {
      id: 'Correcteurs de Carences',
      title: 'Correcteurs de Carences',
      description: 'Solutions complètes pour corriger les déficiences nutritionnelles de vos cultures',
      route: '/produits/correcteurs',
      icon: 'shield',
      imageUrl: 'images/engrais-inorganique.jpg'
    },
    {
      id: 'Engrais Hydrosolubles',
      title: 'Engrais Hydrosolubles',
      description: 'Formulations sans chlore pour une nutrition optimale et progressive',
      route: '/produits/engrais',
      icon: 'droplet',
      imageUrl: 'images/Hydrosouble.jpeg'
    },
    {
      id: 'Lutte Intégrée',
      title: 'Lutte Intégrée',
      description: 'Protection biologique et pollinisation naturelle pour une agriculture durable',
      route: '/produits/lutte-integree',
      icon: 'bug',
      imageUrl: 'images/Ruche-de-bourdons.jpeg'
    },
    {
      id: 'Produits Spéciaux',
      title: 'Produits Spéciaux',
      description: 'Solutions innovantes adaptées à des besoins spécifiques de vos cultures',
      route: '/produits/speciaux',
      icon: 'flask',
      imageUrl: 'images/produit-speciaux.webp'
    },
    {
      id: 'Monitoring',
      title: 'Article Monitoring',
      description: 'Outils de surveillance et de détection pour un contrôle optimal',
      route: '/produits/monitoring',
      icon: 'chart',
      imageUrl: 'images/pheromones-piege-est-un.jpg'
    }
  ];

  ngOnInit(): void {
    // Possibilité de charger les catégories depuis un service
    // this.loadCategories();
  }

  // private loadCategories(): void {
  //   this.productService.getCategories().subscribe(
  //     categories => this.categories = categories
  //   );
  // }
}

