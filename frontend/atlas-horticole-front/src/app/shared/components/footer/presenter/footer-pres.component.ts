import { Component, Input } from '@angular/core';
import { ContactInfo, FooterSection } from './footer-pres.interfaces';

@Component({
  selector: 'app-footer-pres',
  standalone: false,
  templateUrl: './footer-pres.component.html',
  styleUrls: ['./footer-pres.component.scss']
})
export class FooterPresComponent {
  @Input() sections: FooterSection[] = [];
  @Input() contactInfo!: ContactInfo;
  @Input() currentYear: number = new Date().getFullYear();
}