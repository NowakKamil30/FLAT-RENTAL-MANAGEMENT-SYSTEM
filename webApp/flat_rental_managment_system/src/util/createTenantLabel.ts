import { TenantModel } from "../type/TenantModel"

export const createTenantLabel = (tenant: TenantModel) => {
    return `${tenant.firstName} ${tenant.lastName} phone:${tenant.phoneNumber}(${tenant.isActive ? 'ACTIVE' : 'INACTIVE'})`
}