export interface Apartment {
    id: number;
    name: string;
    description: string;
    country: string;
    postcode: string;
    city: string;
    street: string;
    houseNumber: string;
    userId?: string;
    isActive?: boolean;
}