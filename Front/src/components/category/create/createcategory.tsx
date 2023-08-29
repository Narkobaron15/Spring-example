import React from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

import { CategoryCreateItem, catCreateInitVals, catCreateSchema } from '../../../models/category';
import api_common from '../../../requests';
import '../categories.css'
import CUcore from '../cucore';

export default function CreateCategory() {
    const navigate = useNavigate();
    const [requestSent, setRequestSent] = React.useState<boolean>(false);

    const onSubmit = async (val: CategoryCreateItem) => {
        setRequestSent(true);

        await api_common.post('/categories/create', val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => navigate("/"))
            .catch(err => {
                // console.warn(err);

                setRequestSent(false);
                toast.error(err.response.data.message);
            });
    };

    return (
        <>
            <h1 className='form-header'>Create Category Item</h1>
            <CUcore initialValues={catCreateInitVals} validationSchema={catCreateSchema} submit={onSubmit} requestSent={requestSent} />
        </>
    );
}
