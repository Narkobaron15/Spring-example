import React from "react";

import { ProductReadModel } from "../../../models/product/product";
import ProductCardComponent from "./productcard";
import { toast } from "react-toastify";
import api_common from "../../../requests";
import { Link } from "react-router-dom";

export default function ProductCards() {
  const [products, setProducts] = React.useState<ProductReadModel[]>();
  React.useEffect(() => {
    api_common.get('/products')
      .then(r => setProducts(r.data))
      .catch(e => toast.error(e.message));
  }, []);

  return products?.length === 0
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
      <div className="flex flex-row flex-wrap justify-between mx-8">
        {products?.map((product, index) => <ProductCardComponent key={index} product={product} />)}
      </div>
    );
}