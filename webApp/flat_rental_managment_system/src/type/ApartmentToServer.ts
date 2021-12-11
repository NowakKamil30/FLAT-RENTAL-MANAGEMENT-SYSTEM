import { ImageModel } from './ImageModel';
import { ImageToSend } from './ImageToSend';

export interface ApartmentToServer {
    name: string;
    description: string;
    country: string;
    postcode: string;
    city: string;
    street: string;
    houseNumber: string;
    images: ImageToSend[];
    userData: {
        id: number
    };
}