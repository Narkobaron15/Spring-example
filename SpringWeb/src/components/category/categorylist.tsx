import Category from "./category";
import ICategoryItem from '../../models/category';
import './categories.css';

type CategoriesArgs = {
  categories: ICategoryItem[],
};

export default function CategoryList({ categories }: CategoriesArgs) {
  return (
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
  );
}