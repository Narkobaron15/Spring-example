import axios from 'axios';
import APP_ENV from './env/app_env';

// Initialize the API instance for api_common
const api_common = axios.create({
  baseURL: APP_ENV.API_URL,
});

export default api_common;