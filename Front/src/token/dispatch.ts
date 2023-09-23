import jwtDecode from "jwt-decode";
import { IUser, AuthUserActionType } from "../models/Auth";
import api_common from "../requests";
import { store } from "../store/store";

export default function DispatchToken() {
    if (localStorage.token) {
        const { token } = localStorage;
        // set default authorization header for axios
        api_common.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        // decode token
        const user: IUser = jwtDecode(token);
        // and dispatch user to redux store
        store.dispatch({
            type: AuthUserActionType.LOGIN_USER,
            payload: {
                sub: user.sub,
                email: user.email,
                roles: user.roles
            }
        });
    }
}