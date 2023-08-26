import React from 'react'
import { category_common } from './requests';
import ICategoryItem from './models/category';
import CategoryList from './components/category/categorylist';
import Navbar from './components/layout/navbar';
import { Route, Routes } from 'react-router-dom'
import Layout from './components/layout/layout';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Layout/>}>
        <Route index element={<CategoryList/>}></Route>
        {/* <Route></Route> */}
      </Route>
    </Routes>
  )
}

export default App;
