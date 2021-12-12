import { Apartment } from "../type/Apartment";

export const createAdress = (apartment: Apartment) => {
    return `${apartment.street} ${apartment.houseNumber} ${apartment.city}(${apartment.postcode}) ${apartment.country}(${apartment.isActive ? 'ACTIVE' : 'INACTIVE'})`
}