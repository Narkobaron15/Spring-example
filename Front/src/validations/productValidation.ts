import { object, number, string, array } from 'yup';
import { ERROR_MESSAGES, MAX_FILE_SIZE, allowedPicTypes } from '../env/constants';

// multi-file picture validations
const picTest = (value?: any[] | null | undefined) => {
    if (value === null || value === undefined || value.length === 0)
        return true; // attachment is optional

    // File type check (allowed types - jpeg, png, gif)
    for (const file of value) {
        if (!(file instanceof File) || !allowedPicTypes.includes(file.type)) {
            return false;
        }
    }

    return true;
}, sizeTest = (value?: any[] | null | undefined) => {
    if (value === null || value === undefined || value.length === 0)
        return true; // attachment is optional

    // if attached, check every file
    for (const file of value) {
        if (!(file instanceof File) || file.size > MAX_FILE_SIZE)
            return false;
    }

    return true;
}, img_required = (value?: any[] | null | undefined) => {
    return (value instanceof FileList || value instanceof Array) && value.length > 0;
};

const imgValidation = array()
    .test("fileType", ERROR_MESSAGES.NOT_A_PICTURE, picTest)
    .test("fileSize", ERROR_MESSAGES.FILE_TOO_LARGE, sizeTest);

const productSchema = object({
    id: number().nullable(),
    name: string()
        .min(5, ERROR_MESSAGES.TOO_SMALL)
        .max(200, ERROR_MESSAGES.TOO_LARGE)
        .required(ERROR_MESSAGES.REQUIRED),
    price: number()
        .min(0, ERROR_MESSAGES.LESS_THAN_ZERO)
        .required(ERROR_MESSAGES.REQUIRED),
    categoryId: number()
        .integer(ERROR_MESSAGES.INTEGER)
        .min(1, ERROR_MESSAGES.CATEGORY_NOT_FOUND)
        .required(ERROR_MESSAGES.REQUIRED),
    description: string()
        .max(4000, ERROR_MESSAGES.TOO_LARGE)
        .required(ERROR_MESSAGES.REQUIRED),
});

// validation schemas for product create / update operations
export const productCreateSchema = productSchema.shape({
    productImages: imgValidation/*.required(ERROR_MESSAGES.IMG_REQUIRED)*/
        .test("Required", ERROR_MESSAGES.IMG_REQUIRED, img_required)
})
// schema.shape is used to create a new schema based on the other one
export const productUpdateSchema = productSchema.shape({
    newProductImages: imgValidation.nullable(),
    removeProductImages: array().of(number().min(0).integer().required()).nullable(),
});