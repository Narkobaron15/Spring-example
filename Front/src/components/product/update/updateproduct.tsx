import React from "react";

import { useNavigate, useParams } from "react-router-dom";
import { Field, Formik, FormikErrors, FormikTouched, Form, ErrorMessage } from "formik";
import { IProductReadModel, IProductUpdateModel, emptyProduct } from "../../../models/product/product";
import api_common from "../../../requests";
import { productUpdateSchema } from "../../../validations/productValidation";
import FilesComponent from "../../common/file";
import { toast } from "react-toastify";
import ProductImageDTO from "../../../models/product/product_image";

const getErrorComponents = (
    errors: FormikErrors<IProductUpdateModel>,
    touched: FormikTouched<IProductUpdateModel>,
    field: keyof IProductUpdateModel) => {
    return errors[field] && (field === "images" || touched[field])
        ? <div className="error-text">{errors[field]}</div>
        : null;
};

export default function UpdateProduct() {
    // used for redirecting after successful form submit
    const navigate = useNavigate();

    // extract parameters
    const { id } = useParams();

    const [requestSent, setRequestSent] = React.useState<boolean>(false);
    const [categories, setCategories] = React.useState<IProductReadModel[]>([]);
    const [currentProduct, setCurrentProduct] = React.useState<IProductUpdateModel>(emptyProduct);
    const [current_images, setCurrentImages] = React.useState<ProductImageDTO[]>([]);
    const [remove_images, setRemoveImages] = React.useState<number[]>([]);

    React.useEffect(() => {
        const catchFn = (e: Error) => {
            toast.error(e.message);
            // some_logger.log_error(e.message + "\n" + e.stack)
            navigate(`/products`);
        };

        api_common.get(`/categories`)
            .then(r => setCategories(r.data))
            .catch(catchFn);

        api_common.get(`/products/${id}`)
            .then(r => {
                setCurrentImages(r.data.images);
                delete r.data.images; // so they won't be assigned to the input field
                setCurrentProduct(r.data);
            })
            .catch(catchFn);

    }, [navigate, id])

    // the logic of submit button on formik form
    const formikSubmit = async (val: IProductUpdateModel) => {
        setRequestSent(true);

        // check if no inspector manipulations were performed
        // cast needed because of using the select html item
        if (categories.filter(c => c.id === Number(val.category_id)).length === 0) {
            toast.error("Wrong category, please select from given options or refresh the page.");
            return;
        }

        // posting request to update the product
        await api_common.post(`/products/edit/${id}`, val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            })
            .then(() => navigate("/products"))
            .catch(e => {
                toast.error(e);
                setRequestSent(false);
            });
    }

    // Formik component syntax
    // https://formik.org/docs/guides/validation
    return (
        <Formik initialValues={currentProduct}
            enableReinitialize
            validationSchema={productUpdateSchema}
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
                        <h1>{`Оновити продукт ${values.name}`}</h1>
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
                            <label htmlFor="category">Категорія</label>
                        </div>
                        <div className="md:w-10/12">
                            <Field id="category_id" name="category_id" as="select" placeholder="Введіть опис...">
                                {categories.map((cat, i) => <option key={i} value={cat.id}>{cat.name}</option>)}
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
                                Вибрано файлів: {(values.images as Array<File> ?? []).length + current_images.length}
                            </span>
                        </div>
                    </div>
                    <ErrorMessage name="images" component="div" className="error-message"/>
                    <FilesComponent files={values.images} current_files={current_images}
                        setFilesCallback={val => setFieldValue("images", val)}
                        removeCurrentCallback={pic => {
                            setRemoveImages([...remove_images, pic.id]);
                            setCurrentImages(current_images.filter(v => v.id !== pic.id));
                        }} />
                    <div className="flex justify-center">
                        <button className="form-button" type="submit" disabled={requestSent}>Оновити</button>
                    </div>
                </Form>
            )}
        </Formik>
    );
}