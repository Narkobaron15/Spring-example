import React from "react";

import { useNavigate } from "react-router-dom";
import { Field, Formik, FormikErrors, FormikTouched, Form, ErrorMessage } from "formik";

import { IProductCreateModel, emptyProduct } from "../../../models/product/product";
import { CategoryItem } from "../../../models/category/category";
import api_common from "../../../requests";
import { toast } from "react-toastify";
import { productCreateSchema } from "../../../validations/productValidation";
import FilesComponent from "../../common/file";

const getErrorComponents = (
    errors: FormikErrors<IProductCreateModel>,
    touched: FormikTouched<IProductCreateModel>,
    field: keyof IProductCreateModel) => {
    return errors[field] && (field === "images" || touched[field])
        ? <div className="error-text">{errors[field]}</div>
        : null;
};

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
                emptyProduct.category_id = r.data[0].id;
            })
            .catch(e => {
                toast.error(e.message);
                navigate(`/products`);
            });
    }, [])

    // the logic of submit button on formik form
    const formikSubmit = async (val: IProductCreateModel) => {
        setRequestSent(true);

        if (categories.filter(c => c.id === Number(val.category_id)).length === 0) {
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
                        <h1>Додати новий продукт</h1>
                    </div>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="name">Назва</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="name" name="name" type="text" placeholder="Введіть назву..." />
                        </div>
                    </div>
                    <ErrorMessage name="name" component="div" className="error-message"/>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="description">Ціна</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="price" name="price" type="number" placeholder="Вкажіть ціну..." />
                        </div>
                    </div>
                    <ErrorMessage name="price" component="div" className="error-message"/>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="category_id">Категорія</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="category_id" name="category_id" as="select" placeholder="Введіть опис...">
                                {categories.map((cat, i) => <option key={i} value={cat.id!}>{cat.name}</option>)}
                            </Field>
                        </div>
                    </div>
                    <ErrorMessage name="category_id" component="div" className="error-message"/>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="description">Опис</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="description" name="description" type="text" as="textarea" placeholder="Введіть опис..." />
                        </div>
                    </div>
                    <ErrorMessage name="description" component="div" className="error-message"/>
                    <div className="form-group">
                        <div className="md:w-2/12">
                            <label htmlFor="images">Фото</label>
                        </div>
                        <div className="md:w-10/12 flex p-0 bg-gray-200">
                            <div className="inline overflow-hidden w-32">
                                <input type="file" id="images" name="images" multiple onChange={e => {
                                    // file extracting
                                    const files = e.target.files;
                                    if (files) {
                                        setFieldValue(e.target.name, [
                                            ...(values.images as Array<File> ?? []),
                                            ...files
                                        ]);
                                    }
                                }} />
                            </div>
                            <span className="self-center">
                                Вибрано файлів: {(values.images as Array<File> ?? []).length}
                            </span>
                        </div>
                    </div>
                    <ErrorMessage name="images" component="div" className="error-message"/>
                    <div className="form-group justify-evenly flex-wrap">
                        <FilesComponent files={values.images}
                            setFilesCallback={val => setFieldValue("images", val)} />
                    </div>
                    <div className="flex justify-center">
                        <button className="form-button" type="submit" disabled={requestSent}>Додати</button>
                    </div>
                </Form>
            )}
        </Formik>
    );
}