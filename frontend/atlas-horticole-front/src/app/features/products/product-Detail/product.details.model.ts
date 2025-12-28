export interface ProductDetails {
  id: string;
  name: string;
  category: string;
  description: string;
  composition?: string;
  dosage?: string;
  imageUrl?: string;
  price?: number;
  inStock: boolean;
  featured?: boolean;
  
  // Propriétés supplémentaires pour les détails
  technicalSpecs?: TechnicalSpec[];
  usageInstructions?: string[];
  relatedProducts?: RelatedProduct[];
  documentationUrl?: string;
}

export interface TechnicalSpec {
  name: string;
  value: string;
}

export interface RelatedProduct {
  id: string;
  name: string;
  description: string;
  category: string;
  inStock: boolean;
}
