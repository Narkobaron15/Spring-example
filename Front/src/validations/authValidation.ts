import * as Yup from 'yup';

const authValidationSchema = Yup.object().shape({
    email: Yup.string().required("Email is required").email("Invalid email"),
    password: Yup.string().required("Password is required"),
});

export default authValidationSchema;
