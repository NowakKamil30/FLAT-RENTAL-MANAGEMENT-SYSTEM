export interface TenantModel {
    id: number;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    mail: string;
    fee: number;
    isPaid: boolean;
    isActive: boolean;
    description: string;
    endDate: string;
    startDate: string;
    paidDate: string;
    currency: {
        id: number;
        name: string;
    }
}