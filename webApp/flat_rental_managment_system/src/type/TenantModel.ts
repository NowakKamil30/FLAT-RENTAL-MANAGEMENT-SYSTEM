import { ExtraCost } from "./ExtraCost";
import { Document } from './Document';

export interface TenantModel {
    id: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    mail: string;
    fee: number;
    isPaid?: boolean;
    isActive?: boolean;
    description: string;
    endDate?: string;
    startDate: string;
    paidDate?: string;
    documents?: Document[];
    extraCosts?: ExtraCost[];
    currency: {
        id: number | string;
        name?: string;
    },
    apartment?: {
        id: number | string;
    };
    dayToPay?: number;
}