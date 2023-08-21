const api_url: string = import.meta.env.VITE_API_URL;

const app_env = {
    api_url: api_url,
    api_cat_url: api_url + "categories/",
};

export default app_env;