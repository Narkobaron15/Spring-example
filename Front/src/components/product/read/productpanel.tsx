import React from "react";
import ProductRowComponent from "./productrow";
import { ProductReadModel } from "../../../models/product/product";
import { toast } from "react-toastify";
import api_common from "../../../requests";
import { Link } from "react-router-dom";

export default function ProductPanel() {
  const [products, setProducts] = React.useState<ProductReadModel[]>([]);
  React.useEffect(() => {
    api_common.get('/products')
      .then(r => setProducts(r.data))
      .catch(e => toast.error(e.message));
  }, []);

    return products.length === 0
    ? (
      <>
        <h1 className="text-4xl font-bold my-8 text-center">Products</h1>
        <div className='text-center'>
          <p className="text-2xl">Nothing is here yet</p>
          <p className="text-center text-2xl">
            <Link className='font-medium text-blue-600 dark:text-blue-500 hover:underline' to="/products/create">
              Create a new product
            </Link>
          </p>
        </div>
      </>
    )
    : (
      <table>
        <colgroup>
          <col className="w-1/12" />
          <col className="w-1/12" />
          <col className="w-2/12" />
          <col className="w-3/12" />
          <col className="w-1/12" />
          <col className="w-2/12" />
          <col className="w-2/12" />
        </colgroup>
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">{/* Image */}</th>
            <th scope="col">Назва</th>
            <th scope="col">Опис</th>
            <th scope="col">Ціна</th>
            <th scope="col">Категорія</th>
            <th scope="col">Дії</th>
          </tr>
        </thead>
        <tbody>
          {products?.map((item, index) =>
            <ProductRowComponent params={item} key={index}
              removeCallback={() => setProducts(products?.filter(val => val !== item))} />
          )}
        </tbody>
      </table>
    );
}