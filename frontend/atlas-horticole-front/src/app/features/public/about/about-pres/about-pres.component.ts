import { Component, Input } from '@angular/core';
import { AboutSection, Statistic, TimelineItem, ValueItem } from '../../../../core/models/about.model';

@Component({
  selector: 'app-about-pres',
  standalone: false,
  templateUrl: './about-pres.component.html',
  styleUrl: './about-pres.component.scss'
})
export class AboutPresComponent {
  @Input() mainSection!: AboutSection;
  @Input() values: ValueItem[] = [];
  @Input() statistics: Statistic[] = [];
  @Input() timeline: TimelineItem[] = [];
}
