import { InferType } from "yup";
import { catUpdateSchema } from "../validations/categoryValidation";
import ImageDTO from "./image";

export interface CategoryItem {
  get id(): number;
  get name(): string;
  get image(): ImageDTO;
  get description(): string;
}

export const catInitVals: CategoryItem = {
  id: 0,
  name: '',
  image: {
    xs: '',
    sm: '',
    md: '',
    lg: '',
    xl: '',
  },
  description: '',
};

export type CategoryCreateItem = InferType<typeof catUpdateSchema>;
export type CategoryUpdateItem = InferType<typeof catUpdateSchema>;

export const catCreateInitVals: CategoryCreateItem = {
  name: '',
  image: null,
  description: '',
};