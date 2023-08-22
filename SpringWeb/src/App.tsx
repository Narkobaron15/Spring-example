import React from 'react'
import { category_common } from './requests';
import ICategoryItem from './models/category';
import CategoryList from './components/category/categorylist';

function App() {
  const [categories, setCategories] = React.useState<ICategoryItem[]>([]);

  React.useEffect(() => {
    category_common.get('')
      .then(response => setCategories(response.data))
      .catch(error => console.error('Error fetching categories:', error));
  }, []);

  return (
    <main className='container mx-auto'>
      <h1 className="text-4xl font-bold mb-5 text-center">Categories</h1>
      <CategoryList categories={categories} />
    </main>
  );
}

export default App;
