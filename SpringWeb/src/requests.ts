import axios from 'axios';
import app_env from './env/app_env';

// Initialize the API instance for api_common
const api_common = axios.create({
    baseURL: app_env.api_url,
  });
// Initialize the API instance for category_common
const category_common = axios.create({
  baseURL: app_env.api_cat_url,
});

export { category_common, api_common };
