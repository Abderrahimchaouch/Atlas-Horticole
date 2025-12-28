import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MenuItem } from '../container/header.interface';

@Component({
  selector: 'app-header-pres',
  standalone: false,
  templateUrl: './header-pres.component.html',
  styleUrl: './header-pres.component.scss'
})
export class HeaderPresComponent {
  @Input() menuItems: MenuItem[] = [];
  @Input() isScrolled: boolean = false;
  @Input() isMobileMenuOpen: boolean = false;
  @Input() activeSubmenu: string | null = null;

  @Output() toggleMobileMenu = new EventEmitter<void>();
  @Output() toggleSubmenu = new EventEmitter<string>();
  @Output() closeMobileMenu = new EventEmitter<void>();
  @Output() submenuNavigation = new EventEmitter<{ route: string, filter?: string }>();

  onToggleMobileMenu(): void {
    this.toggleMobileMenu.emit();
  }

  onToggleSubmenu(menuLabel: string): void {
    this.toggleSubmenu.emit(menuLabel);
  }

  onCloseMobileMenu(): void {
    this.closeMobileMenu.emit();
  }

  onSubmenuNavigation(route: string, filter?: string): void {
    this.submenuNavigation.emit({ route, filter });
  }
}