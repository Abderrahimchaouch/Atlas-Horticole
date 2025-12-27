import { Component, HostListener, OnInit } from '@angular/core';
import { MenuItem } from './header.interface';


@Component({
  selector: 'app-header-cont',
  standalone:false,
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
      children: [
        { label: 'Biostimulants', route: '/produits/biostimulants' },
        { label: 'Correcteurs de Carences', route: '/produits/correcteurs' },
        { label: 'Engrais Hydrosolubles', route: '/produits/engrais' },
        { label: 'Lutte Intégrée', route: '/produits/lutte-integree' },
        { label: 'Produits Spéciaux', route: '/produits/speciaux' },
        { label: 'Article Monitoring', route: '/produits/monitoring' }
      ]
    },
    {
      label: 'Solutions',
      icon: 'solutions',
      children: [
        { label: 'Amendement de Sol', route: '/solutions/amendement' },
        { label: 'Protection des Cultures', route: '/solutions/protection' },
        { label: 'Nutrition des Plantes', route: '/solutions/nutrition' },
        { label: 'Biocontrôle', route: '/solutions/biocontrole' }
      ]
    },
    {
      label: 'Services',
      route: '/services',
      icon: 'services'
    },
    {
      label: 'Catalogue',
      route: '/catalogue',
      icon: 'catalog'
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

  ngOnInit(): void {
    // Initialize component
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
}