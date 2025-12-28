export interface NewsArticle {
    id: string;
    title: string;
    excerpt: string;
    content: string;
    imageUrl?: string;
    date: Date;
    author: string;
    category: string;
    tags: string[];
    readTime: number; // en minutes
    featured?: boolean;
}

export interface NewsFilter {
    category: string;
    searchQuery: string;
}

export interface RelatedArticle {
    id: string;
    title: string;
    excerpt: string;
    date: Date;
    category: string;
}