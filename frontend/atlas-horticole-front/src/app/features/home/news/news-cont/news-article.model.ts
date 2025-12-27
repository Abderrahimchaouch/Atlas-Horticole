export interface NewsArticle {
    id: string;
    title: string;
    excerpt: string;
    imageUrl?: string;
    date: Date;
    route: string;
    category?: string;
}