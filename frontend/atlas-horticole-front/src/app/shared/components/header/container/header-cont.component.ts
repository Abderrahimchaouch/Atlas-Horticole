import { Component, HostListener, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { MenuItem } from './header.interface';

@Component({
  selector: 'app-header-cont',
  standalone: false,
  templateUrl: './header-cont.component.html',
  styleUrl: './header-cont.component.scss'
})
export class HeaderContComponent implements OnInit {
  isScrolled = false;
  isMobileMenuOpen = false;
  activeSubmenu: string | null = null;

  menuItems: MenuItem[] = [
    {
      label: 'Accueil',
      route: '/',
      icon: 'home'
    },
    {
      label: 'Produits',
      icon: 'products',
      route: '/produits',
      children: [
        {
          label: 'Biostimulants',
          route: '/produits',
          filter: 'Biostimulants'
        },
        {
          label: 'Correcteurs de Carences',
          route: '/produits',
          filter: 'Correcteurs de Carences'
        },
        {
          label: 'Engrais Hydrosolubles',
          route: '/produits',
          filter: 'Engrais Hydrosolubles'
        },
        {
          label: 'Lutte Intégrée',
          route: '/produits',
          filter: 'Lutte Intégrée'
        },
        {
          label: 'Produits Spéciaux',
          route: '/produits',
          filter: 'Produits Spéciaux'
        },
        {
          label: 'Article Monitoring',
          route: '/produits',
          filter: 'Monitoring'
        }
      ]
    },
    {
      label: 'Articles',
      route: '/articles',
      icon: 'articles'
    },
    {
      label: 'Catalogue',
      route: '/catalogue',
      icon: 'catalogue'
    },
    {
      label: 'À Propos',
      route: '/a-propos',
      icon: 'about'
    },
    {
      label: 'Contact',
      route: '/contact',
      icon: 'contact'
    }
  ];

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.handleCloseMobileMenu();
    });
  }

  @HostListener('window:scroll', [])
  onWindowScroll(): void {
    this.isScrolled = window.scrollY > 50;
  }

  handleToggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
    if (!this.isMobileMenuOpen) {
      this.activeSubmenu = null;
    }
  }

  handleToggleSubmenu(menuLabel: string): void {
    this.activeSubmenu = this.activeSubmenu === menuLabel ? null : menuLabel;
  }

  handleCloseMobileMenu(): void {
    this.isMobileMenuOpen = false;
    this.activeSubmenu = null;
  }

  handleSubmenuNavigation(route: string, filter?: string): void {
    this.handleCloseMobileMenu();

    if (filter) {
      this.router.navigate([route], {
        queryParams: { category: filter },
        queryParamsHandling: 'merge'
      });
    } else {
      this.router.navigate([route]);
    }
  }
}