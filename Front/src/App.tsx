import { Route, Routes } from 'react-router-dom'

import CategoryList from './components/category/read/categorylist';
import Layout from './components/layout/layout';
import NotFound from './components/errors/notfound';
import CreateCategory from './components/category/create/createcategory';
import EditCategory from './components/category/update/updatecategory';

export default function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        {/* <Route path='/products'>
          <Route index element={}></Route>
        </Route> */}
        <Route index element={<CategoryList />}></Route>
        <Route path='/categories'>
          <Route index element={<CategoryList />} />
          <Route path="create" element={<CreateCategory />} />
          <Route path='update/:id' element={<EditCategory />} />
        </Route>
      </Route>
      <Route path='*' element={<NotFound />} />
    </Routes>
  )
}
