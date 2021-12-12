import { TenantModel } from "./TenantModel";

export interface Document {
    id: number;
    name: string;
    document: string;
    tenant: TenantModel;
}