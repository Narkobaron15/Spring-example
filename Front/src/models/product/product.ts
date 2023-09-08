import { InferType } from "yup";
import { productCreateSchema, productUpdateSchema } from "../../validations/productValidation";
import CategoryImageDTO from "./category_image";

export type IProductCreateModel = InferType<typeof productCreateSchema>;
export type IProductUpdateModel = InferType<typeof productUpdateSchema>;

export interface IProductReadModel {
    get id(): number;
    get name(): string;
    get price(): number;
    get description(): string;
    get category_id(): number;
    get category_name(): string;
    get primary_image(): CategoryImageDTO;
    get images(): CategoryImageDTO[];
}

export const emptyProduct: IProductCreateModel = {
    id: null,
    name: "",
    price: 0,
    category_id: 0,
    description: "",
    images: [],
};