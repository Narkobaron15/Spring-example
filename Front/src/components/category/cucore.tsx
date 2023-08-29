import { Formik, Form, Field, ErrorMessage, FormikHelpers } from 'formik';
import { CategoryCreateItem } from '../../models/category';
import { AnyObject, ObjectSchema } from 'yup';
import { useNavigate } from 'react-router-dom';

const nullCategory: any = { name: undefined, imageURL: undefined, description: undefined };

type CUcoreArgs = {
    initialValues: CategoryCreateItem,
    validationSchema: ObjectSchema<CategoryCreateItem, AnyObject, typeof nullCategory>,
    submit: (values: CategoryCreateItem, formikHelpers: FormikHelpers<CategoryCreateItem>) => void | Promise<any>,
    requestSent: boolean,
    update: boolean,
}

export default function CUcore({ initialValues, validationSchema, submit, requestSent, update = false }: CUcoreArgs) {
    const navigate = useNavigate();

    return (
        <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={submit} enableReinitialize>
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
                <div className="flex justify-end mb-4">
                    <button type="button" className="cancel-btn" onClick={() => navigate(-1)}>Cancel</button>
                    <button type="submit" className="create-btn" disabled={requestSent}>{update ? "Update" : "Create"}</button>
                </div>
            </Form>
        </Formik>
    )
}