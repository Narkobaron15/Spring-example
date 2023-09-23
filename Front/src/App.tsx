import { Route, Routes } from 'react-router-dom'

import CategoryPanel from './components/category/read/categorypanel';
import Layout from './components/layout/layout';
import NotFound from './components/errors/notfound';
import CreateCategory from './components/category/create/createcategory';
import UpdateCategory from './components/category/update/updatecategory';
import ProductCards from './components/product/read/productcards';
import ProductPanel from './components/product/read/productpanel';
import CreateProduct from './components/product/create/createproduct';
import UpdateProduct from './components/product/update/updateproduct';
import ProductDetails from './components/product/read/productdetails';
import AuthPage from './components/auth/login/authpage';
import RegisterPage from './components/auth/register/registerpage';

export default function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<ProductCards />}></Route>
        <Route path='/products'>
          <Route path=':id' element={<ProductDetails />} />
          <Route path="create" element={<CreateProduct />} />
          <Route path='update/:id' element={<UpdateProduct />} />
        </Route>
        <Route path='/account'>
          {/* <Route index element={<AccountPage />} /> */}
          <Route path='auth' element={<AuthPage />} />
          <Route path='register' element={<RegisterPage />} />
        </Route>
        <Route path='/admin'>
          {/* <Route index element={<AdminPage />} /> */}
          <Route path='products' element={<ProductPanel />}></Route>
          <Route path='categories'>
            <Route index element={<CategoryPanel />} />
            <Route path="create" element={<CreateCategory />} />
            <Route path='update/:id' element={<UpdateCategory />} />
          </Route>
        </Route>
      </Route>
      <Route path='*' element={<NotFound />} />
    </Routes>
  )
}
