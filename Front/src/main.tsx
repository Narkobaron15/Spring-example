import ReactDOM from 'react-dom/client'

import { BrowserRouter } from 'react-router-dom'
import 'flowbite'

import App from './App.tsx'
import './index.css'
import { Provider } from 'react-redux'
import { store } from './store/store.ts'
import DispatchToken from './token/dispatch.ts'

DispatchToken();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Provider store={store}>
      <App />
    </Provider>
  </BrowserRouter>,
)
