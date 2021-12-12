import { ExtraCost } from "../type/ExtraCost";

export const createExtraCostLabel = (extraCost: ExtraCost, currencyName: string): string => (
    `${extraCost.name}(${extraCost.extraCost} ${currencyName})`
)