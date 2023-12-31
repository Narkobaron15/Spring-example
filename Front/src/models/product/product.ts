import { InferType } from "yup";
import { productCreateSchema, productUpdateSchema } from "../../validations/productValidation";
import ProductImageDTO from "./product_image";

export type IProductCreateModel = InferType<typeof productCreateSchema>;
export type IProductUpdateModel = InferType<typeof productUpdateSchema>;

// convert into class
export class ProductReadModel {
    constructor(
        private _id: number,
        private _name: string,
        private _price: number,
        private _description: string,
        private _categoryId: number,
        private _categoryName: string,
        private _images: ProductImageDTO[]
    ) { }

    get id(): number {
        return this._id;
    }

    get name(): string {
        return this._name;
    }

    get price(): number {
        return this._price;
    }

    get description(): string {
        return this._description;
    }

    get categoryId(): number {
        return this._categoryId;
    }

    get categoryName(): string {
        return this._categoryName;
    }

    get primary_image(): ProductImageDTO {
        return this._images.find(img => img.priority === 0)!;
    }

    get images(): ProductImageDTO[] {
        return this._images;
    }

    static ofObject(obj: ProductReadModel): ProductReadModel {
        return new ProductReadModel(
            obj.id,
            obj.name,
            obj.price,
            obj.description,
            obj.categoryId,
            obj.categoryName,
            obj.images,
        );
    }
}

export const emptyProduct: IProductCreateModel = {
    id: null,
    name: "",
    price: 0,
    categoryId: 0,
    description: "",
    productImages: [],
};