import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

import { CategoryCreateItem, catCreateSchema, CategoryItem, catInitVals } from '../../../models/category';
import api_common from '../../../requests';
import '../categories.css'
import CUcore from '../cucore';

export default function EditCategory() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [requestSent, setRequestSent] = React.useState<boolean>(false);
    const [category, setCategory] = React.useState<CategoryItem>(catInitVals);

    React.useEffect(() => {
        api_common.get('/categories/' + id)
            .then(r => setCategory(r.data))
            .catch(() => {
                toast.error("This category doesn't exist.");
                navigate('/');
            });
    }, [id]);

    const onSubmit = (val: CategoryCreateItem) => {
        setRequestSent(true);

        api_common.put('/categories/' + category.id, val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => navigate("/"))
            .catch(err => {
                setRequestSent(false);
                toast.error(err.message);
            });
    };

    return (
        <>
            <h1 className='form-header'>Edit category {category.name}</h1>
            <CUcore
                initialValues={category}
                validationSchema={catCreateSchema}
                submit={onSubmit}
                requestSent={requestSent}
                update={true} />
        </>
    );
}