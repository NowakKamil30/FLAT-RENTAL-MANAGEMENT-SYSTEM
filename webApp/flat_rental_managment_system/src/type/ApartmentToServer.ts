import { ImageModel } from './ImageModel';

export interface ApartmentToServer {
    name: string;
    description: string;
    country: string;
    postcode: string;
    city: string;
    street: string;
    houseNumber: string;
    images: ImageModel[];
    userData: {
        id: number
    };
}