import { object, InferType, string, mixed } from 'yup';
import { ERROR_MESSAGES, MAX_FILE_SIZE, allowedPicTypes } from '../env/constants';

// single-file picture validations
// attachment is optional in this checks
const picTest = (value: any) => {
    // File type check - allowed types are jpeg, png, gif, svg
    // attachment is optional
    return value ? allowedPicTypes.includes(value.type) : true; 
}, sizeTest = (value: any) => {
    if (!value || !value.length) return true; // optional
    return value[0].size <= MAX_FILE_SIZE; // 20 MB
};
const imgValidation = mixed()
    .test("fileType", ERROR_MESSAGES.NOT_A_PICTURE, picTest)
    .test("fileSize", ERROR_MESSAGES.FILE_TOO_LARGE, sizeTest);

export const catCreateSchema = object({
    name: string()
        .required(ERROR_MESSAGES.REQUIRED)
        .min(5, 'Name should be at least 5 characters long'),
    image: imgValidation
        .required(ERROR_MESSAGES.IMG_REQUIRED),
    description: string()
        .required(ERROR_MESSAGES.REQUIRED)
        .min(10, 'Description should be at least 10 characters long'),
});
export const catUpdateSchema = catCreateSchema.shape({ image: imgValidation.nullable() });