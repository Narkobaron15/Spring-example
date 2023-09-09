import React from "react";

import { useNavigate } from "react-router-dom";
import { Field, Formik, Form, ErrorMessage } from "formik";

import { IProductCreateModel, emptyProduct } from "../../../models/product/product";
import { CategoryItem } from "../../../models/category/category";
import api_common from "../../../requests";
import { toast } from "react-toastify";
import { productCreateSchema } from "../../../validations/productValidation";
import FilesComponent from "../../common/file";

import '../product.css';
import Dropzone from "../../common/dropzone/dropzone";

export default function CreateProduct() {
    // used for redirecting after successful form submit
    const navigate = useNavigate();

    const [requestSent, setRequestSent] = React.useState<boolean>(false);
    const [categories, setCategories] = React.useState<CategoryItem[]>([]);

    React.useEffect(() => {
        api_common.get(`/categories`)
            .then(r => {
                if (r.data.length === 0) {
                    navigate('/categories/create');
                    toast.error('Create a new category to assign to the product');
                    return;
                }

                setCategories(r.data);
                emptyProduct.categoryId = r.data[0].id;
            })
            .catch(e => {
                toast.error(e.message);
                navigate(`/products`);
            });
    }, [])

    // the logic of submit button on formik form
    const formikSubmit = async (val: IProductCreateModel) => {
        setRequestSent(true);

        if (categories.filter(c => c.id === Number(val.categoryId)).length === 0) {
            toast.error("Wrong category, please select from given options or refresh the page.");
            return;
        }

        // posting request to create the product onto creation path
        await api_common.post("/products/create", val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => navigate("/products"))
            .catch(e => {
                toast.error(e.message);
                setRequestSent(false);
            });
    }

    // Formik component syntax
    // https://formik.org/docs/guides/validation
    return (
        <Formik initialValues={emptyProduct}
            enableReinitialize
            validationSchema={productCreateSchema}
            onSubmit={formikSubmit}>
            {
                /*
                 * can be either with or without this function
                 * this arrow function wrapper is used to
                 * catch formik state objects and hooks
                 */
            }
            {({ values, setFieldValue }) => (
                <Form className="mx-auto">
                    <div className="form-group justify-center">
                        <h1>Create a new category</h1>
                    </div>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="name">Name</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="name" name="name" type="text" placeholder="Введіть назву..." />
                        </div>
                    </div>
                    <ErrorMessage name="name" component="div" className="error-message" />
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="description">Price</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="price" name="price" type="number" placeholder="Вкажіть ціну..." />
                        </div>
                    </div>
                    <ErrorMessage name="price" component="div" className="error-message" />
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="categoryId">Category</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="categoryId" name="categoryId" as="select" placeholder="Введіть опис...">
                                {categories.map((cat, i) => <option key={i} value={cat.id!}>{cat.name}</option>)}
                            </Field>
                        </div>
                    </div>
                    <ErrorMessage name="categoryId" component="div" className="error-message" />
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="description">Description</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="description" name="description" type="text" as="textarea" placeholder="Введіть опис..." />
                        </div>
                    </div>
                    <ErrorMessage name="description" component="div" className="error-message" />
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="images">Image</label>
                        </div>
                        <div className="md:w-10/12 p-0">
                            <div className="inline overflow-hidden w-32">
                                <Dropzone id="images" name="productImages" multiple={true} fileOnChange={e => {
                                    // file extracting
                                    const files = e.target.files;
                                    if (files) {
                                        setFieldValue(e.target.name, [
                                            ...(values.productImages ?? []),
                                            ...files
                                        ]);
                                    }
                                }} />
                            </div>
                            <span className="self-center px-4 py-2 bg-gray-200 rounded">
                                Вибрано файлів: {(values.productImages ?? []).length}
                            </span>
                        </div>
                    </div>
                    <ErrorMessage name="productImages" component="div" className="error-message" />
                    <FilesComponent files={values.productImages}
                        setFilesCallback={val => setFieldValue("images", val)} />
                    <div className="flex justify-center">
                        <button className="form-button" type="submit" disabled={requestSent}>Додати</button>
                    </div>
                </Form>
            )}
        </Formik>
    );
}