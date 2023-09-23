import { ErrorMessage, Field, Form, Formik } from "formik";
import { useNavigate } from "react-router-dom";
import { ILogin, ILoginResult } from "../../../models/Auth";
import api_common from "../../../requests";
import { toast } from "react-toastify";
import loginSchema from '../../../validations/authValidation';

import '../auth.css';
import { multipartHeaders } from "../../../env/constants";
import DispatchToken from "../../../token/dispatch";

const initialValues: ILogin = {
    email: "",
    password: "",
};

export default function AuthPage() {
    const navigate = useNavigate();

    const handleSubmit = async (values: ILogin) => {
        try {
            const result = await api_common.post<ILoginResult>(
                "/account/login",
                values,
                multipartHeaders
            );

            // save token to local storage
            const token = result.data.token;
            localStorage.setItem("token", token);
            DispatchToken();
            
            navigate("/");
        } catch {
            toast.error("Invalid email or password");
        }
    };

    return (
        <>
            <div className="mx-auto text-center">
                <h1 className="text-3xl  font-bold text-black sm:text-4xl">Вхід</h1>
            </div>
            <Formik
                initialValues={initialValues}
                onSubmit={handleSubmit}
                validationSchema={loginSchema}
            >
                <Form className="auth-form">
                    <i
                        className="bi bi-arrow-left-circle-fill back-button"
                        onClick={() => navigate("..")}
                    ></i>
                    <div className="mb-4">
                        <Field type="text" id="email" name="email" placeholder="Email" />
                        <ErrorMessage name="email" component="div" className="error-message" />
                    </div>
                    <div className="mb-4">
                        <Field type="password" id="password" name="password" placeholder="Password" />
                        <ErrorMessage name="password" component="div" className="error-message" />
                    </div>
                    <button type="submit">Login</button>
                </Form>
            </Formik>
        </>
    );
}