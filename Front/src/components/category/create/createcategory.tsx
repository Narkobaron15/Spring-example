import React from 'react';
import { useNavigate } from 'react-router-dom';

import { Formik, Form, Field, ErrorMessage } from 'formik';
import { toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

import api_common from '../../../requests';

import { catCreateSchema } from '../../../validations/categoryValidation';

import '../categories.css';
import defaultPic from '../../../assets/upload.svg';
import { CategoryCreateItem, catCreateInitVals } from '../../../models/category';

export default function CreateCategory() {
    const navigate = useNavigate();
    const [requestSent, setRequestSent] = React.useState<boolean>(false);

    const onSubmit = (val: CategoryCreateItem) => {
        setRequestSent(true);

        api_common.post('/categories/create', val,
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
            <h1 className='form-header'>Create Category Item</h1>

            <Formik initialValues={catCreateInitVals} validationSchema={catCreateSchema} onSubmit={onSubmit} enableReinitialize>
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
                                            : defaultPic
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
                            <button type="submit" className="create-btn" disabled={requestSent}>Create</button>
                        </div>
                    </Form>
                )}
            </Formik>
        </>
    );
}
