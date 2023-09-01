import React from 'react'
import { useNavigate } from 'react-router-dom';

import { Formik, Form, Field, ErrorMessage, FormikHelpers } from 'formik';
import { AnyObject, ObjectSchema } from 'yup';

import { CategoryCreateItem } from '../../validations/categoryValidation';
import defaultPic from '../../assets/upload.svg';
import './categories.css';

const nullCategory: any = { name: undefined, imageURL: undefined, description: undefined };

type CUcoreArgs = {
    initialValues: CategoryCreateItem,
    initialImgUrl?: string,
    validationSchema: ObjectSchema<CategoryCreateItem, AnyObject, typeof nullCategory>,
    submit: (values: CategoryCreateItem, formikHelpers: FormikHelpers<CategoryCreateItem>) => void | Promise<any>,
    requestSent: boolean,
    update: boolean,
}

export default function CUcore({ initialValues, initialImgUrl, validationSchema, submit, requestSent, update }: CUcoreArgs) {
    const navigate = useNavigate();
    return (
        <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={submit} enableReinitialize>
            {({ values, setFieldValue }) => (
                <Form className='form'>
                    <div className="mb-4">
                        <label htmlFor="name">Name</label>
                        <Field type="text" id="name" name="name" />
                        <ErrorMessage name="name" component="div" className="error-message" />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="imageURL">Image URL</label>
                        <Field type="text" id="imageURL" name="imageURL" />
                        <ErrorMessage name="imageURL" component="div" className="error-message" />
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
                                        : (initialImgUrl ?? defaultPic)
                                } />
                            <span className="sr-only">Choose a category photo</span>
                            <input className="file w-5/6" aria-describedby="file_input_help" id="file_input" name="image" type="file" onChange={e => {
                                const files = e.target.files;
                                if (!files || !(files instanceof FileList)) return;
                                const file = files[0];
                                setFieldValue("image", file);
                            }} />
                        </div>
                        <p className="mt-1 text-sm text-gray-500 dark:text-gray-300" id="file_input_help">SVG, PNG, JPG or GIF</p>
                    </div>
                    <div className="flex justify-end mb-4">
                        <button type="button" className="cancel-btn" onClick={() => navigate(-1)}>Cancel</button>
                        <button type="submit" className="create-btn" disabled={requestSent}>{update ? "Update" : "Create"}</button>
                    </div>
                </Form>
            )}
        </Formik>
    )
}