import React, { useState } from 'react';
import { Box } from '@mui/system';
import { Button } from '@mui/material';
import Snackbar from '../Snackbar';
import { ErrorModel } from '../../type/ErrorModel';
import { Document } from '../../type/Document';
import DocumentInput from './DocumentInput';

export interface DocumentInputControllerProps {
    documents: Document[];
    setDocuments: (documents: Document[]) => void;
}

const DocumentInputController: React.FC<DocumentInputControllerProps> = ({
    documents,
    setDocuments
}): JSX.Element => {
    const [error, setError] = useState<ErrorModel>({message: ''});
    return (
        <>
        {
            !!documents ?
            <Box
            component='div'>
                <Button 
                color='secondary'
                disabled={documents[documents.length - 1]?.document.length < 1}
                onClick={() => {
                    setDocuments([...documents, {document: '', name: '', id: Math.random()}]);
                }}
                >
                    Add next Document
                </Button>
                {documents?.map((document, i) => <DocumentInput
                    key={document.id}
                    document={document}
                    onChange={(e) => {
                        const fileReader = new FileReader();
                        if(e.target.files.length > 0) {
                            if (e.target.files[0].size > 4194304) {
                                setError({...error, message: 'document is to big'})
                                return null;
                            } else if (e.target.files[0].size <= 50) {
                                setError({...error, message: 'document is to small'})
                                return null;
                            } else if (!String(e.target.files[0].type).startsWith('application')) {
                                 setError({...error, message: 'it is not document'})
                                 return null;
                            }
                            fileReader.readAsDataURL(e.target.files[0]);
                            fileReader.onload = () => {
                                documents[documents.findIndex(documentItem => document.id === documentItem.id)] = {document: fileReader.result+'', name: e.target.files[0].name, id: document.id}
                                setDocuments([...documents]);
                            }
                        }
                    }}
                    onDeleteClick={() => {
                        documents.splice(documents.findIndex(documentItem => documentItem.id === document.id), 1)
                        setDocuments([...documents])
                    }}
                />)}
                <Snackbar
                open={ error.message.length > 0 }
                autoHideDuration={ 5000 }
                severity='error'
                title={error.message}
                onClose={() => setError({...error, message: ''})}
                />
            </Box>
            :
            null
        }
        </>      
    )
};

export default DocumentInputController;