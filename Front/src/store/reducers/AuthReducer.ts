import { AuthUserActionType, AuthUserActions, IAuthUser } from "../../models/Auth.ts";

const initState: IAuthUser = {
    isAuth: false,
    user: undefined,
};

export const AuthReducer = (state = initState, action: AuthUserActions): IAuthUser => {
    switch (action.type) {
        case AuthUserActionType.LOGIN_USER: {
            return {
                isAuth: true,
                user: action.payload,
            };
        }
        case AuthUserActionType.LOGOUT_USER: {
            return initState;
        }
    }
    
    return state;
};