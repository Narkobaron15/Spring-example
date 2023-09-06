import React from 'react';
import '../categories.css';
import CategoryComponent from './category';

import api_common from '../../../requests';
import { CategoryItem } from '../../../models/category';
import { Link } from 'react-router-dom';

export default function CategoryList() {
  const [categories, setCategories] = React.useState<CategoryItem[]>([]);

  React.useEffect(() => {
    api_common.get('/categories/')
      .then(response => setCategories(response.data))
      .catch(error => console.error('Error fetching categories:', error));
  }, []);

  return (
    <>
      <h1 className="text-4xl font-bold mb-5 text-center">Categories</h1>
      {categories.length === 0
        ? (
          <div className='mt-10'>
            <p className="text-center text-2xl">Nothing is here yet</p>
            <p className="text-center text-2xl">
              <Link className='font-medium text-blue-600 dark:text-blue-500 hover:underline' to="/categories/create">
                Create a new category
              </Link>
            </p>
          </div>
        )
        : (
          <table>
            <colgroup>
              <col className="w-1/12 flex" />
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
              {categories.map((cat, index) =>
                <CategoryComponent
                  instance={cat}
                  removeCallback={() => setCategories(categories.filter(x => x !== cat))}
                  key={index} />)}
            </tbody>
          </table>
        )}
    </>
  );
}
