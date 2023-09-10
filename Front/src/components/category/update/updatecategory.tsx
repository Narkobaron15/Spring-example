import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { Formik, Form, Field, ErrorMessage } from 'formik';
import { toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

import api_common from '../../../requests';

import { CategoryItem, CategoryUpdateItem, catInitVals } from '../../../models/category/category';
import { catUpdateSchema } from '../../../validations/categoryValidation';

import defaultPic from '../../../assets/upload.svg';
import '../categories.css';

export default function UpdateCategory() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [requestSent, setRequestSent] = React.useState<boolean>(false);
    const [category, setCategory] = React.useState<CategoryItem>(catInitVals);
    const [initialImg, setInitialImg] = React.useState<string>();

    React.useEffect(() => {
        api_common.get('/categories/' + id)
            .then(r => {
                const data = r.data;
                setInitialImg(data.image.md);
                delete data.image;
                setCategory(data);
            })
            .catch(() => {
                toast.error("This category doesn't exist.");
                navigate('/');
            });
    }, [id]);

    const onSubmit = (val: CategoryUpdateItem) => {
        setRequestSent(true);

        api_common.put('/categories/' + category.id, val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => navigate("/categories/"))
            .catch(err => {
                setRequestSent(false);
                toast.error(err.message);
            });
    };

    return (
        <>
            <h1 className='form-header'>Edit category {category.name}</h1>
            <Formik initialValues={category} validationSchema={catUpdateSchema} onSubmit={onSubmit} enableReinitialize>
                {({ values, setFieldValue }) => (
                    <Form className='form'>
                        <div className="mb-4">
                            <label htmlFor="name">Name</label>
                            <Field type="text" id="name" name="name" />
                            <ErrorMessage name="name" component="div" className="error-message" />
                        </div>
                        <div className="mb-4">
                            <label htmlFor="description">Description</label>
                            <Field as="textarea" id="description" name="description" />
                            <ErrorMessage name="description" component="div" className="error-message" />
                        </div>
                        <div className="mb-4">
                            <label htmlFor="image">Photo</label>
                            <div className="flex justify-between mt-2 p-0">
                                <img className="h-10 w-10 mr-3 object-fit rounded-full inline-block" alt="Current category"
                                    src={
                                        values.image instanceof File
                                            ? URL.createObjectURL(values.image)
                                            : (initialImg ?? defaultPic)
                                    } />
                                <span className="sr-only">Choose a category photo</span>
                                <input className="file w-5/6" aria-describedby="file_input_help" id="image" name="image" type="file" onChange={e => {
                                    const files = e.target.files;
                                    if (!files || !(files instanceof FileList)) return;
                                    const file = files[0];
                                    setFieldValue("image", file);
                                }} />
                            </div>
                            <p className="mt-1 text-sm text-gray-500 dark:text-gray-300" id="file_input_help">SVG, PNG, JPG or GIF</p>
                            <ErrorMessage name="image" component="div" className="error-message" />
                        </div>
                        <div className="flex justify-end mb-4">
                            <button type="button" className="cancel-btn" onClick={() => navigate(-1)}>Cancel</button>
                            <button type="submit" className="create-btn" disabled={requestSent}>Update</button>
                        </div>
                    </Form>
                )}
            </Formik>
        </>
    );
}