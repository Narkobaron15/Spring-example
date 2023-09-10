import React from "react";

import { useNavigate, useParams } from "react-router-dom";
import { Field, Formik, Form, ErrorMessage } from "formik";
import { toast } from "react-toastify";

import { ProductReadModel, IProductUpdateModel, emptyProduct } from "../../../models/product/product";
import api_common from "../../../requests";
import { productUpdateSchema } from "../../../validations/productValidation";
import FilesComponent from "../../common/file/file";
import ProductImageDTO from "../../../models/product/product_image";
import Dropzone from "../../common/dropzone/dropzone";

import '../product.css';

export default function UpdateProduct() {
    // used for redirecting after successful form submit
    const navigate = useNavigate();

    // extract parameters
    const { id } = useParams();

    const [requestSent, setRequestSent] = React.useState<boolean>(false);
    const [categories, setCategories] = React.useState<ProductReadModel[]>([]);
    const [currentProduct, setCurrentProduct] = React.useState<IProductUpdateModel>(emptyProduct);
    const [current_images, setCurrentImages] = React.useState<ProductImageDTO[]>([]);

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

                delete r.data.images;
                delete r.data.categoryName;
                setCurrentProduct(r.data);
            })
            .catch(catchFn);

    }, [navigate, id])

    // the logic of submit button on formik form
    const formikSubmit = (val: IProductUpdateModel) => {
        setRequestSent(true);

        // check if no inspector manipulations were performed
        // cast needed because of using the select html item
        if (categories.filter(c => c.id === Number(val.categoryId)).length === 0) {
            toast.error("Wrong category, please select from given options or refresh the page.");
            return;
        }

        // posting request to update the product
        api_common.put(`/products/${id}`, val,
            { // http request params
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            }).then(() => navigate("/products"))
            .catch(e => {
                toast.error(e);
                setRequestSent(false);
            });
    };

    return (
        <Formik initialValues={currentProduct}
            enableReinitialize
            validationSchema={productUpdateSchema}
            onSubmit={formikSubmit}>
            {({ values, setFieldValue }) => (
                <Form className="mx-auto product-form">
                    <div className="form-group justify-center">
                        <h1>{`Update product '${values.name}'`}</h1>
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
                            <label htmlFor="price">Price</label>
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
                                {categories.map((cat, i) => <option key={i} value={cat.id}>{cat.name}</option>)}
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
                            <span>Pictures</span>
                        </div>
                        <div className="md:w-10/12 p-0">
                            <div className="inline overflow-hidden w-32">
                                <Dropzone id="images" name="newProductImages" multiple={true} fileOnChange={e => {
                                    // file extracting
                                    const files = e.target.files;
                                    if (files) {
                                        setFieldValue(e.target.name, [
                                            ...(values.newProductImages ?? []),
                                            ...files
                                        ]);
                                    }
                                }} />
                            </div>
                            <p className="files-counter">
                                Вибрано файлів: {(values.newProductImages ?? []).length + current_images.length}
                            </p>
                        </div>
                    </div>
                    <ErrorMessage name="newProductImages" component="div" className="error-message" />
                    <FilesComponent files={values.newProductImages} current_files={current_images}
                        setFilesCallback={val => setFieldValue("images", val)}
                        removeCurrentCallback={pic => {
                            setFieldValue("removeProductImages", [...values.removeProductImages ?? [], pic.id]);
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