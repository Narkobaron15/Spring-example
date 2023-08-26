import React from 'react';
import ICategoryItem from '../../models/category';
import { category_common } from '../../requests';
import './categories.css';
import Category from './category';

export default function CategoryList() {
  const [categories, setCategories] = React.useState<ICategoryItem[]>([]);

  React.useEffect(() => {
    category_common.get('')
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
          <col className="w-3/12" />
          <col className="w-7/12" />
          {/* Add action buttons */}
        </colgroup>
        <thead>
          <tr>
            <th scope='col'></th>
            <th scope='col'>ID</th>
            <th scope='col'>Name</th>
            <th scope='col'>Description</th>
          </tr>
        </thead>
        <tbody>
          {categories.map(cat => <Category category={cat} key={cat.id} />)}
        </tbody>
      </table>
    </>
  );
}
