export interface Product {
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
}

export interface ProductFilter {
    category: string;
    searchQuery: string;
    inStockOnly: boolean;
}