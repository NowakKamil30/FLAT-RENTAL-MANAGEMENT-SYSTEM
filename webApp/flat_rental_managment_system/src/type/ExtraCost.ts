import { TenantModel } from "./TenantModel";

export interface ExtraCost {
    id: number;
    extraCost: number;
    name: string;
    tenant: TenantModel;
}