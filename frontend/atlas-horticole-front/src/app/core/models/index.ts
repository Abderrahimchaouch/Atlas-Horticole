// src/app/core/models/index.ts

// Export direct des interfaces
export type { TimelineItem, Statistic, ValueItem, AboutSection } from './about.model';
export type { RelatedArticle, NewsFilter, NewsArticle } from './articles.model';
export type { CatalogDocument, DownloadForm } from './catalogue.model';
export type { ContactInfo, ContactForm } from './contact.model';
export type {ProductCategory} from './product-categories.model';
export type {ProductFilter,Product} from './products.model'
export type {RelatedProduct,TechnicalSpec,ProductDetails} from './product.details.model'

// Types utiles
export type ApiResponse<T> = {
  data: T;
  message?: string;
  success: boolean;
};

export type PaginatedResponse<T> = {
  items: T[];
  total: number;
  page: number;
  pageSize: number;
  totalPages: number;
};