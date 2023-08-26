import React from 'react';
import ICategoryItem from '../../models/category';
import api_common from '../../requests';
import './categories.css';
import Category from './category';

export default function CategoryList() {
  const [categories, setCategories] = React.useState<ICategoryItem[]>([]);

  React.useEffect(() => {
    api_common.get('/categories/')
      .then(response => setCategories(response.data))
      .catch(error => console.error('Error fetching categories:', error));
  }, []);

  return (
    <>
      <h1 className="text-4xl font-bold mb-5 text-center">Categories</h1>
      <table>
        <colgroup>
          <col className="w-1/12" />
          <col className="w-1/12" />
          <col className="w-2/12" />
          <col className="w-6/12" />
          <col className="w-2/12" />
        </colgroup>
        <thead>
          <tr>
            <th scope="col"></th>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Actions</th>
          </tr>
        </thead>
        <tbody>
          {categories.map(cat => <Category category={cat} key={cat.id} />)}
        </tbody>
      </table>
    </>
  );
}
