import { IProductReadModel } from "../../../models/product";
import arrowimg from '../../../assets/arrow.svg'
import { Link } from "react-router-dom";
import StringUtils from "../../utils/stringutils";

type ProductCardArgs = {
    product: IProductReadModel,
};

const MAX_HEADER_LENGTH = 45;

export default function ProductCardComponent({ product }: ProductCardArgs) {
    const productLink = `/products/details/${product.id}`;

    return (
        <div className="card">
            <img className="primary-img" src={product.primary_image.lg} alt="" />
            <div className="p-5">
                <h5>
                    <Link to={productLink}>
                        {StringUtils.ShortenString(product.name, MAX_HEADER_LENGTH)}
                    </Link>
                </h5>
                <p>
                    {product.category_name} |
                    {StringUtils.PriceToString(product.price)}
                </p>
                <Link to={productLink} className="tailwind-btn-dark">
                    Детальніше
                    <img src={arrowimg} alt="Arrow" className="w-3.5 h-3.5 ml-2 mt-0.5" />
                </Link>
            </div>
        </div>

    );
}