import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../../../core/models/products.model';

@Component({
  selector: 'app-products-cont',
  standalone: false,
  templateUrl: './products-cont.component.html',
  styleUrl: './products-cont.component.scss'
})
export class ProductsContComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: string[] = ['Tous', 'Biostimulants', 'Correcteurs de Carences', 'Engrais Hydrosolubles', 'Lutte Intégrée', 'Produits Spéciaux', 'Monitoring'];
  selectedCategory = 'Tous';
  searchQuery = '';
  inStockOnly = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router // Ajouter Router
  ) { }

  ngOnInit(): void {
    this.loadProducts();

    // Écouter les paramètres de l'URL
    this.route.queryParams.subscribe(params => {
      if (params['category']) {
        this.selectedCategory = params['category'];
      }
      this.applyFilters();
    });
  }

  private loadProducts(): void {
    this.products = [
      // Biostimulants
      {
        id: 'organic-sol',
        name: 'ORGANIC SOL',
        category: 'Biostimulants',
        description: 'Amendement de sol 100% végétale avec acides organiques, vitamines et acides aminés',
        composition: 'Matière organique: 35%, Azote: 2%, Potassium: 3%',
        dosage: '2 applications/mois - 5 L/ha',
        inStock: true,
        featured: true
      },

      // Correcteurs de Carences
      {
        id: 'extramix-eddha',
        name: 'EXTRAMIX EDDHA',
        category: 'Correcteurs de Carences',
        description: 'Complexe de micronutriments chélatés EDTA et EDDHA',
        composition: 'Fe: 7.5%, Mn: 3.3%, Zn: 0.6%, Cu: 0.2%, B: 0.5%',
        dosage: 'Foliaire: 50-200 g/hl, Sol: 1.5-2 g/L',
        inStock: true,
        featured: true
      },
      {
        id: 'extramix-sb',
        name: 'EXTRAMIX SB',
        category: 'Correcteurs de Carences',
        description: 'Micronutriments sans bore chélatés à l\'EDTA',
        composition: 'Fe: 7.5%, Mn: 3.3%, Zn: 0.6%, Cu: 0.2%',
        dosage: 'Foliaire: 50-200 g/hl, Sol: 1.5-2 g/L',
        inStock: true
      },
      {
        id: 'ferrolas',
        name: 'FERROLAS 4.9',
        category: 'Correcteurs de Carences',
        description: 'Correcteur chlorose ferrique, chélaté EDDHA',
        composition: 'Fer (Fe) chélaté EDDHA: 6%, Isomère O-O: 4.9%',
        dosage: 'Application au sol: 1.5-2 kg/ha',
        inStock: true
      },
      {
        id: 'k-plus',
        name: 'K PLUS',
        category: 'Correcteurs de Carences',
        description: 'Concentré liquide riche en potassium',
        composition: 'Azote: 3%, Potassium K₂O: 32.8%',
        dosage: 'Foliaire: 150-300 cc/hl, Sol: 5-10 L/ha',
        inStock: true
      },
      {
        id: 'atlas-ca',
        name: 'ATLAS CA',
        category: 'Correcteurs de Carences',
        description: 'Concentré azote et calcium avec nanoparticules GBA',
        composition: 'Azote: 9%, Calcium CaO: 15%',
        dosage: 'Irrigation: 20-40 L/ha (arbres), 10-30 L/ha (maraîchères)',
        inStock: true,
        featured: true
      },
      {
        id: 'calcibor-flow',
        name: 'CALCIBOR FLOW',
        category: 'Correcteurs de Carences',
        description: 'Suspension calcium, bore et zinc',
        composition: 'Calcium CaO: 15%, Bore: 0.2%, Zinc: 1.8%',
        dosage: 'Irrigation: 10 L/ha (chaque 7-10 jours), Foliaire: 3-5 L/ha',
        inStock: true
      },
      {
        id: 'nitrofort',
        name: 'NITROFORT',
        category: 'Correcteurs de Carences',
        description: 'Engrais azoté stabilisé triple action',
        composition: 'Azote total: 30% (ammoniacal: 7.5%, nitrique: 15%, uréique: 7.5%)',
        dosage: 'Racinaire: 15-30 L/ha, Foliaire: max 300/ha',
        inStock: true
      },
      {
        id: 'ultra-mn',
        name: 'ULTRA Mn FLOW',
        category: 'Correcteurs de Carences',
        description: 'Suspension concentrée de manganèse',
        composition: 'Manganèse (Mn) total: 27.4%',
        dosage: 'Foliaire: 50-75 cc/hl',
        inStock: true
      },
      {
        id: 'ultra-zn',
        name: 'ULTRA Zn FLOW',
        category: 'Correcteurs de Carences',
        description: 'Suspension concentrée de zinc',
        composition: 'Zinc (Zn) total: 40%',
        dosage: 'Foliaire: 200-300 cc/hl, Sol: 2-4 L/ha',
        inStock: true
      },
      {
        id: 'atlas-bor',
        name: 'ATLAS BOR 17%',
        category: 'Correcteurs de Carences',
        description: 'Correcteur de carence en bore',
        composition: 'Bore (B): 17%',
        dosage: 'Racinaire: 5-12 L/ha',
        inStock: true
      },
      {
        id: 'abaxsal-plus',
        name: 'ABAXSAL PLUS',
        category: 'Correcteurs de Carences',
        description: 'Correcteur calcium et déplacement des sels',
        composition: 'Calcium CaO: 12% (complexé)',
        dosage: 'Racinaire: 5-12 L/ha',
        inStock: true
      },

      // Engrais Hydrosolubles
      {
        id: 'atlas-13-40-13',
        name: 'ATLAS 13-40-13 TE',
        category: 'Engrais Hydrosolubles',
        description: 'Engrais cristallin pour vivier, repiquage et préfloraison',
        composition: 'N: 13%, P₂O₅: 40%, K₂O: 13% + microéléments',
        inStock: true,
        featured: true
      },
      {
        id: 'atlas-15-05-30',
        name: 'ATLAS 15-05-30 TE',
        category: 'Engrais Hydrosolubles',
        description: 'Engrais pour croissance culture et fruits',
        composition: 'N: 15%, P₂O₅: 5%, K₂O: 30% + microéléments',
        inStock: true
      },
      {
        id: 'atlas-20-20-20',
        name: 'ATLAS 20-20-20 TE',
        category: 'Engrais Hydrosolubles',
        description: 'Engrais équilibré pour croissance active',
        composition: 'N: 20%, P₂O₅: 20%, K₂O: 20% + microéléments',
        inStock: true
      },
      {
        id: 'atlas-07-12-40',
        name: 'ATLAS 07-12-40-2-MGO TE',
        category: 'Engrais Hydrosolubles',
        description: 'Engrais pour maturation des fruits',
        composition: 'N: 7%, P₂O₅: 12%, K₂O: 40%, MgO: 2% + microéléments',
        inStock: true
      },

      // Produits Spéciaux
      {
        id: 'pero-gold',
        name: 'PERO GOLD',
        category: 'Produits Spéciaux',
        description: 'Désinfectant à base d\'acide peracétique 5%',
        composition: 'Peroxyde hydrogène <30%, Acide acétique <25%, Acide peracétique <5%',
        dosage: 'Foliaire: 1-2%, Irrigation: 3-5 L/ha',
        inStock: true
      },
      {
        id: 'sical',
        name: 'SICAL',
        category: 'Produits Spéciaux',
        description: 'Fortifiant tissus - calcium et silicium',
        composition: 'Calcium CaO: 20%, Silicium Si₂O: 23%',
        dosage: 'Foliaire: 100-300 cc/hl, Irrigation: 3-10 L/ha',
        inStock: true
      },
      {
        id: 'reguant-ph',
        name: 'REGUANT PH',
        category: 'Produits Spéciaux',
        description: 'Régulateur pH et antimoussant',
        composition: 'Azote: 3%, P₂O₅: 15%',
        dosage: '0.4-1.5/1000L d\'eau',
        inStock: true
      },
      {
        id: 'gluco-cuivre',
        name: 'GLUCO-CUIVRE',
        category: 'Produits Spéciaux',
        description: 'Cuivre complexé acide heptagluconique',
        composition: 'Cuivre (Cu) complexé HGA: 6.5%',
        dosage: 'Foliaire: 200-300 cc/hl, Sol: 2-5 L/ha',
        inStock: true
      },

      // Lutte Intégrée
      {
        id: 'orius-lavigatus',
        name: 'ORIUS LAVIGATUS',
        category: 'Lutte Intégrée',
        description: 'Punaise de lit - 1000 individus',
        dosage: '1.5-3 ind/m², plusieurs applications dès les premières fleurs',
        inStock: true,
        featured: true
      },
      {
        id: 'nesidiocoris',
        name: 'NESIDIOCORIS TENUIS',
        category: 'Lutte Intégrée',
        description: 'Punaise de lit - 500 individus',
        dosage: '0.5-1.5 ind/m², selon culture et période',
        inStock: true
      },
      {
        id: 'amblyseius',
        name: 'AMBLYSEIUS SWIRSKII',
        category: 'Lutte Intégrée',
        description: 'Flacon 25000 + Sachets 250 ind/enveloppe',
        dosage: '50-150 ind/m²',
        inStock: true
      },
      {
        id: 'aphidius-colemani',
        name: 'APHIDIUS COLEMANI',
        category: 'Lutte Intégrée',
        description: 'Momies émergent 500-1000-5000 adultes',
        dosage: '0.5-2 ind/m², introductions hebdomadaires 0.25 ind/m²',
        inStock: true
      },
      {
        id: 'eretmocerus',
        name: 'ERETMOCERUS EREMICUS',
        category: 'Lutte Intégrée',
        description: 'Pupes 5000 adultes',
        dosage: '1.5-3 ind/m² selon quantité aleurodes',
        inStock: true
      },
      {
        id: 'phytoseiulus',
        name: 'PHYTOSEIULUS PERSIMILIS',
        category: 'Lutte Intégrée',
        description: 'Flacon 10000 acariens',
        dosage: '2-6 ind/m² ou 20 ind/m² sur araignées rouges',
        inStock: true
      },
      {
        id: 'aphidius-matricariae',
        name: 'APHIDIUS MATRICARIAE',
        category: 'Lutte Intégrée',
        description: 'Momies 500-1000-5000 adultes',
        dosage: '0.5-2 ind/m², introductions hebdomadaires',
        inStock: true
      },
      {
        id: 'ruche-bourdon',
        name: 'RUCHE BOURDON',
        category: 'Lutte Intégrée',
        description: 'Ruche d\'hiver bourdons cultures protégées',
        dosage: 'Tomate, aubergine, poivron, courgette, melon, fraise',
        inStock: true
      },

      // Monitoring
      {
        id: 'pheromones-tuta',
        name: 'PHEROMONES TUTA ABSOLUTA',
        category: 'Monitoring',
        description: 'Phéromones pour détection Tuta Absoluta',
        inStock: true
      },
      {
        id: 'rouleaux-adhesifs',
        name: 'ROULEAUX ADHESIFS',
        category: 'Monitoring',
        description: 'Rouleaux adhésifs pour piégeage',
        inStock: true
      },
      {
        id: 'plaques-adhesifs',
        name: 'PLAQUES ADHESIFS',
        category: 'Monitoring',
        description: 'Plaques adhésives pour monitoring',
        inStock: true
      },
      {
        id: 'pieges-delta',
        name: 'PIEGES DELTA',
        category: 'Monitoring',
        description: 'Pièges delta pour surveillance',
        inStock: true
      },

      // Atlas Bio Compost
      {
        id: 'atlas-bio-compost',
        name: 'ATLAS BIO COMPOST',
        category: 'Biostimulants',
        description: 'Amendement organique compost végétal certifié',
        composition: 'Matière organique: 45%, Acides humiques: 6.7%, Acides fulviques: 5.2%, N: 1.5%, P₂O₅: 2.1%, K₂O: 4.5%',
        inStock: true,
        featured: true
      }
    ];
  }

  handleCategoryChange(category: string): void {
    this.selectedCategory = category;
    this.applyFilters();
  }

  handleSearchChange(query: string): void {
    this.searchQuery = query;
    this.applyFilters();
  }

  handleStockFilterChange(checked: boolean): void {
    this.inStockOnly = checked;
    this.applyFilters();
  }

  handleProductDetails(productId: string): void {
    // Navigation vers la page de détails
    this.router.navigate(['/produits', productId]);
  }
  handlerRequestQuote(): void {
    this.router.navigate(['/contact']);
  }

  private applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesCategory = this.selectedCategory === 'Tous' || product.category === this.selectedCategory;
      const matchesSearch = !this.searchQuery ||
        product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        product.description.toLowerCase().includes(this.searchQuery.toLowerCase());
      const matchesStock = !this.inStockOnly || product.inStock;

      return matchesCategory && matchesSearch && matchesStock;
    });
  }
}
