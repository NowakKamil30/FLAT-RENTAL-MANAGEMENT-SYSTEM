import {Document} from '../type/Document';

export const createDocumentLabel = (document: Document): string => (
    `${document.name}`
)